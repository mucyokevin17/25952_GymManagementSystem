package fitness.tracker.rw.service;

import fitness.tracker.rw.model.PasswordResetToken;
import fitness.tracker.rw.model.UserProfile;
import fitness.tracker.rw.repository.PasswordResetTokenRepository;
import fitness.tracker.rw.repository.UserProfileRepository;
import fitness.tracker.rw.util.JwtTokenUtil;
import fitness.tracker.rw.util.TwoFactorUtil;
import fitness.tracker.rw.util.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final fitness.tracker.rw.repository.UserRepository userRepository;
    private final fitness.tracker.rw.repository.TrainerRepository trainerRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final JavaMailSender mailSender;
    private final EmailService emailService;

    // In-memory 2FA code store
    private final Map<String, String> stored2FACodes = new ConcurrentHashMap<>();

    // ========== USER REGISTRATION ==========
    public UserProfile register(String email, String password, String fullName, String role) {
        if (userProfileRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        UserProfile user = new UserProfile();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setFullName(fullName);
        user.setRole(role);
        return userProfileRepository.save(user);
    }

    // ========== LOGIN + 2FA ==========
    public Map<String, Object> loginWithToken(String email, String password) {
        Optional<UserProfile> optionalUser = userProfileRepository.findByEmail(email);
        if (optionalUser.isEmpty() || !passwordEncoder.matches(password, optionalUser.get().getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        UserProfile user = optionalUser.get();

        // Generate 2FA code
        String twoFactorCode = TwoFactorUtil.generate2FACode();
        stored2FACodes.put(email, twoFactorCode);

        // Send 2FA email
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("2FA Verification Code");
            helper.setText("Your 2FA code is: " + twoFactorCode);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send 2FA email");
        }

        // Return temporary response (frontend will handle 2FA)
        return Map.of(
                "message", "2FA code sent to email",
                "email", email);
    }

    public Map<String, Object> completeLoginWith2FA(String email, String code) {
        if (!verify2FACode(email, code)) {
            throw new RuntimeException("Invalid 2FA code");
        }

        UserProfile user = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtTokenUtil.generateToken(email, user.getRole());

        Map<String, Object> userData = new HashMap<>();
        userData.put("email", user.getEmail());
        userData.put("fullName", user.getFullName());
        userData.put("role", user.getRole());

        // Link to specific entity based on role
        if ("USER".equalsIgnoreCase(user.getRole())) {
            userRepository.findByEmail(email).ifPresent(u -> userData.put("userId", u.getUserId()));
        } else if ("TRAINER".equalsIgnoreCase(user.getRole())) {
            trainerRepository.findByEmail(email).ifPresent(t -> userData.put("trainerId", t.getId()));
        }

        return Map.of(
                "token", token,
                "user", userData);
    }

    /**
     * Verifies the 2FA code for the given email.
     */
    private boolean verify2FACode(String email, String code) {
        String storedCode = stored2FACodes.get(email);
        if (storedCode != null && storedCode.equals(code)) {
            stored2FACodes.remove(email); // Invalidate code after use
            return true;
        }
        return false;
    }

    // ========== PROFILE MANAGEMENT ==========
    public Optional<UserProfile> getUserProfileByEmail(String email) {
        return userProfileRepository.findByEmail(email);
    }

    public UserProfile updateUserProfileByEmail(String email, UserProfile updatedProfile) {
        UserProfile user = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setFullName(updatedProfile.getFullName());
        user.setRole(updatedProfile.getRole());
        if (updatedProfile.getPassword() != null && !updatedProfile.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updatedProfile.getPassword()));
        }
        return userProfileRepository.save(user);
    }

    public void deleteUserProfileByEmail(String email) {
        UserProfile user = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userProfileRepository.delete(user);
    }

    public List<UserProfile> findAll() {
        return userProfileRepository.findAll();
    }

    // ========== PASSWORD RESET FLOW ==========
    public void createPasswordResetToken(UserProfile user) {
        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));

        tokenRepository.save(resetToken);

        String resetLink = "http://localhost:5173/reset-password?token=" + token;
        emailService.send(user.getEmail(), "Password Reset Request", buildResetEmail(resetLink));
    }

    public void resetPasswordWithToken(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token has expired");
        }

        UserProfile user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userProfileRepository.save(user);

        tokenRepository.delete(resetToken); // Clean up
    }

    private String buildResetEmail(String resetLink) {
        return "Hello,\n\n"
                + "You requested to reset your password. Please click the link below to proceed:\n"
                + resetLink + "\n\n"
                + "This link will expire in 30 minutes.\n\n"
                + "If you did not request this, please ignore the email.\n\n"
                + "Thanks,\nFitness Tracker Team";
    }

    public UserProfile findByEmail(String email) {
        return userProfileRepository.findByEmail(email).orElse(null);
    }

    // ========== IMAGE UPLOAD ==========
    public UserProfile uploadAvatar(String email, MultipartFile file) {
        UserProfile user = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            String uploadDir = "uploads/avatars/" + email;
            Files.createDirectories(Paths.get(uploadDir));

            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null && originalFilename.contains(".")
                    ? originalFilename.substring(originalFilename.lastIndexOf('.'))
                    : ".jpg";

            String filename = UUID.randomUUID() + extension;
            Path filePath = Paths.get(uploadDir, filename);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String avatarUrl = "/uploads/avatars/" + email + "/" + filename;
            user.setAvatarUrl(avatarUrl);

            return userProfileRepository.save(user);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }
}
