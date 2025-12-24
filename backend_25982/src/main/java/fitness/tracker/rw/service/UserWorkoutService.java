package fitness.tracker.rw.service;

import fitness.tracker.rw.dto.UserWorkoutDTO;
import fitness.tracker.rw.dto.UserDTO;
import fitness.tracker.rw.dto.WorkoutDTO;
import fitness.tracker.rw.dto.TrainerDTO;        // ← add this import
import fitness.tracker.rw.model.UserWorkout;
import fitness.tracker.rw.model.User;
import fitness.tracker.rw.model.Workout;
import fitness.tracker.rw.repository.UserWorkoutRepository;
import fitness.tracker.rw.repository.UserRepository;
import fitness.tracker.rw.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserWorkoutService {

    @Autowired private UserWorkoutRepository userWorkoutRepository;
    @Autowired private UserRepository      userRepository;
    @Autowired private WorkoutRepository   workoutRepository;

    public UserWorkoutDTO convertToDTO(UserWorkout uw) {
        User u = uw.getUser();
        UserDTO userDTO = new UserDTO(
            u.getUserId(),
            u.getFullName(),
            u.getEmail(),
            u.getHeight(),
            u.getWeight(),
            u.getGoal(),
            // map the trainer on the User
            u.getTrainer() != null
                ? new TrainerDTO(
                    u.getTrainer().getId(),
                    u.getTrainer().getName(),
                    u.getTrainer().getCertification())
                : null
        );

        Workout w = uw.getWorkout();
        WorkoutDTO workoutDTO = new WorkoutDTO(
            w.getWorkoutId(),
            w.getWorkoutName()
        );

        return new UserWorkoutDTO(
            uw.getId(),
            userDTO,
            workoutDTO,
            uw.getDatePerformed(),
            uw.getDurationCompleted(),
            uw.getNotes()
        );
    }

    public UserWorkout convertToEntity(UserWorkoutDTO dto) {
        UserWorkout uw = new UserWorkout();
        uw.setId(dto.getId());

        if (dto.getUser() != null) {
            User u = userRepository.findById(dto.getUser().getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found"));
            uw.setUser(u);
        }

        if (dto.getWorkout() != null) {
            Workout w = workoutRepository.findById(dto.getWorkout().getWorkoutId())
                        .orElseThrow(() -> new RuntimeException("Workout not found"));
            uw.setWorkout(w);
        }

        uw.setDatePerformed(dto.getDatePerformed());
        uw.setDurationCompleted(dto.getDurationCompleted());
        uw.setNotes(dto.getNotes());
        return uw;
    }

    public List<UserWorkoutDTO> getAllUserWorkoutDTOs() {
        return userWorkoutRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserWorkoutDTO> getUserWorkoutDTOById(Long id) {
        return userWorkoutRepository.findById(id)
                .map(this::convertToDTO);
    }

    public UserWorkoutDTO saveUserWorkout(UserWorkoutDTO dto) {
        UserWorkout saved = userWorkoutRepository.save(convertToEntity(dto));
        return convertToDTO(saved);
    }

    public void deleteUserWorkout(Long id) {
        userWorkoutRepository.deleteById(id);
    }

    public List<UserWorkoutDTO> getUserWorkoutDTOsByUser(String fullName) {
        return userWorkoutRepository.findByUser_FullName(fullName)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<UserWorkoutDTO> getUserWorkoutDTOsByWorkout(String workoutName) {
        return userWorkoutRepository.findByWorkout_WorkoutName(workoutName)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<UserWorkoutDTO> getUserWorkoutDTOsByUserId(Long userId) {
        return userWorkoutRepository.findByUser_UserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
