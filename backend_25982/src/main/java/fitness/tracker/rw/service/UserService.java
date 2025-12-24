package fitness.tracker.rw.service;

import fitness.tracker.rw.dto.TrainerDTO;
import fitness.tracker.rw.dto.UserDTO;
import fitness.tracker.rw.model.Trainer;
import fitness.tracker.rw.model.User;
import fitness.tracker.rw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private fitness.tracker.rw.repository.LocationRepository locationRepository;

    @Autowired
    private fitness.tracker.rw.repository.TrainerRepository trainerRepository;

    public List<UserDTO> getAllUserDTOs() {
        return userRepository.findAll().stream()
                .map(this::mapUserToDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserDTOById(Long userId) {
        return userRepository.findById(userId)
                .map(this::mapUserToDTO);
    }

    public Optional<UserDTO> getUserDTOByName(String fullName) {
        return userRepository.findByFullName(fullName)
                .map(this::mapUserToDTO);
    }

    public Optional<UserDTO> getUserDTOByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::mapUserToDTO);
    }

    public UserDTO saveUser(UserDTO userDto) {
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setHeight(userDto.getHeight());
        user.setWeight(userDto.getWeight());
        user.setGoal(userDto.getGoal());

        if (userDto.getLocationId() != null) {
            locationRepository.findById(userDto.getLocationId()).ifPresent(user::setLocation);
        }

        if (userDto.getTrainerId() != null) {
            trainerRepository.findById(userDto.getTrainerId()).ifPresent(user::setTrainer);
        }

        return mapUserToDTO(userRepository.save(user));
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<UserDTO> getUserDTOsByProgram(String programName) {
        return userRepository.findByUserPrograms_Program_ProgramName(programName)
                .stream()
                .map(this::mapUserToDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getUserDTOsByWorkout(String workoutName) {
        return userRepository.findByUserWorkouts_Workout_WorkoutName(workoutName)
                .stream()
                .map(this::mapUserToDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getUserDTOsByTrainer(String trainerName) {
        return userRepository.findByTrainer_Name(trainerName)
                .stream()
                .map(this::mapUserToDTO)
                .collect(Collectors.toList());
    }

    private UserDTO mapUserToDTO(User user) {
        Trainer trainer = user.getTrainer();
        TrainerDTO trainerDTO = null;

        if (trainer != null) {
            trainerDTO = new TrainerDTO(trainer.getId(), trainer.getName(), trainer.getCertification());
        }

        String hierarchy = null;
        if (user.getLocation() != null) {
            StringBuilder sb = new StringBuilder();
            fitness.tracker.rw.model.Location loc = user.getLocation();
            java.util.Stack<String> stack = new java.util.Stack<>();
            while (loc != null) {
                stack.push(loc.getName());
                loc = loc.getParent();
            }
            while (!stack.isEmpty()) {
                sb.append(stack.pop());
                if (!stack.isEmpty())
                    sb.append(" / ");
            }
            hierarchy = sb.toString();
        }

        return new UserDTO(
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getHeight(),
                user.getWeight(),
                user.getGoal(),
                trainerDTO,
                user.getLocation() != null ? user.getLocation().getId() : null,
                user.getLocation() != null ? user.getLocation().getName() : null,
                hierarchy);
    }

    public Optional<UserDTO> updateUserByEmail(String email, UserDTO userDto) {
        return userRepository.findByEmail(email)
                .map(existingUser -> {
                    // Verify the DTO's ID matches the existing user's ID (if provided)
                    if (userDto.getUserId() != null && !userDto.getUserId().equals(existingUser.getUserId())) {
                        throw new IllegalArgumentException("User ID cannot be changed");
                    }

                    // Update fields (with null checks)
                    if (userDto.getFullName() != null) {
                        existingUser.setFullName(userDto.getFullName());
                    }
                    if (userDto.getEmail() != null) {
                        existingUser.setEmail(userDto.getEmail());
                    }
                    if (userDto.getHeight() != 0) { // assuming 0 is not a valid height
                        existingUser.setHeight(userDto.getHeight());
                    }
                    if (userDto.getWeight() != 0) { // assuming 0 is not a valid weight
                        existingUser.setWeight(userDto.getWeight());
                    }
                    if (userDto.getGoal() != null) {
                        existingUser.setGoal(userDto.getGoal());
                    }

                    // Update Location
                    if (userDto.getLocationId() != null) {
                        locationRepository.findById(userDto.getLocationId()).ifPresent(existingUser::setLocation);
                    }

                    // Update Trainer
                    if (userDto.getTrainerId() != null) {
                        trainerRepository.findById(userDto.getTrainerId()).ifPresent(existingUser::setTrainer);
                    }

                    User updatedUser = userRepository.save(existingUser);
                    return mapUserToDTO(updatedUser);
                });
    }
}
