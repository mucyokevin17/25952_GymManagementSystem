package fitness.tracker.rw.repository;

import fitness.tracker.rw.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByFullName(String fullName);

    List<User> findByUserPrograms_Program_ProgramName(String programName);

    List<User> findByUserWorkouts_Workout_WorkoutName(String workoutName);

    List<User> findByTrainer_Name(String trainerName);
} 
