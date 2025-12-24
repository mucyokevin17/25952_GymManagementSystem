package fitness.tracker.rw.repository;

import fitness.tracker.rw.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    Optional<Workout> findByWorkoutName(String workoutName);
    List<Workout> findByUserWorkouts_User_FullName(String fullName);
    List<Workout> findByTrainer_Name(String trainerName);
    List<Workout> findByPrograms_ProgramName(String programName);
}