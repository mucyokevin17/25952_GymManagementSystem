package fitness.tracker.rw.controller;

import fitness.tracker.rw.model.UserProfile;
import fitness.tracker.rw.repository.PasswordResetTokenRepository;
import fitness.tracker.rw.repository.UserProfileRepository;
import fitness.tracker.rw.service.UserProfileService;
import jakarta.servlet.http.HttpServletRequest;
import fitness.tracker.rw.dto.LoginRequest;
import fitness.tracker.rw.dto.RegisterRequest;
import fitness.tracker.rw.dto.ResetPasswordRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Map;

// Import your JwtTokenUtil class (adjust the package if needed)
import fitness.tracker.rw.util.JwtTokenUtil;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    // ========= Register =========
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            UserProfile user = userProfileService.register(
                    request.getEmail(),
                    request.getPassword(),
                    request.getFullName(),
                    request.getRole()
            );
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ========= Login =========
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Map<String, Object> response = userProfileService.loginWithToken(
                    request.getEmail(),
                    request.getPassword()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

// ========= 2FA Verification =========
    @PostMapping("/verify-2fa")
    public ResponseEntity<?> verify2FA(@RequestParam String email, @RequestParam String enteredCode) {
        try {
            Map<String, Object> authResponse = userProfileService.completeLoginWith2FA(email, enteredCode);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

// ========= Get Current User =========
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7); // Remove "Bearer "
            String email = jwtTokenUtil.extractEmail(token);

            UserProfile user = userProfileService.findByEmail(email);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            return ResponseEntity.ok(Map.of(
                    "id", user.getId(),
                    "email", user.getEmail(),
                    "fullName", user.getFullName(),
                    "role", user.getRole(),
                    "avatarUrl", user.getAvatarUrl()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    // ========= Request Password Reset =========
    @PostMapping("/request-reset")
    public ResponseEntity<?> requestPasswordReset(@RequestParam String email) {
        Optional<UserProfile> optionalUser = userProfileRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Email not found.");
        }

        UserProfile user = optionalUser.get();
        userProfileService.createPasswordResetToken(user);
        return ResponseEntity.ok("Password reset link sent to email.");
    }

    // ========= Reset Password With Token =========
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestParam String token,
            @RequestBody ResetPasswordRequest request
    ) {
        String newPassword = request.getNewPassword();

        if (newPassword == null || newPassword.isBlank()) {
            return ResponseEntity.badRequest().body("Missing or empty newPassword.");
        }

        try {
            userProfileService.resetPasswordWithToken(token, newPassword);
            return ResponseEntity.ok("Password successfully reset.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ========= Get by Email =========
    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        return userProfileService.getUserProfileByEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ========= Update by Email =========
    @PutMapping("/{email}")
    public ResponseEntity<?> updateUser(@PathVariable String email, @RequestBody UserProfile updatedProfile) {
        try {
            UserProfile user = userProfileService.updateUserProfileByEmail(email, updatedProfile);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{email}")
    public ResponseEntity<?> patchUser(@PathVariable String email, @RequestBody UserProfile updatedProfile) {
        try {
            UserProfile user = userProfileService.updateUserProfileByEmail(email, updatedProfile);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ========= Upload Avatar =========
    @PostMapping("/{email}/upload-avatar")
    public ResponseEntity<?> uploadAvatar(
            @PathVariable String email,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Uploaded file is empty");
            }

            if (file.getSize() > 5 * 1024 * 1024) {
                return ResponseEntity.badRequest().body("File size exceeds 5MB limit");
            }

            String contentType = file.getContentType();
            if (contentType == null
                    || !(contentType.equals("image/jpeg")
                    || contentType.equals("image/png")
                    || contentType.equals("image/gif"))) {
                return ResponseEntity.badRequest().body("Only JPEG, PNG, and GIF images are allowed");
            }

            // Save file and update user
            UserProfile user = userProfileService.uploadAvatar(email, file);

            // Build full URL
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            String fullAvatarUrl = baseUrl + user.getAvatarUrl(); // avatarUrl should be like `/uploads/...`

            return ResponseEntity.ok(Map.of(
                    "avatarUrl", fullAvatarUrl,
                    "message", "Avatar uploaded successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload avatar: " + e.getMessage());
        }
    }

    // ========= Delete by Email =========
    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email) {
        try {
            userProfileService.deleteUserProfileByEmail(email);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ========= Get All Users =========
    @GetMapping
    public ResponseEntity<List<UserProfile>> getAllUsers() {
        return ResponseEntity.ok(userProfileService.findAll());
    }
}
