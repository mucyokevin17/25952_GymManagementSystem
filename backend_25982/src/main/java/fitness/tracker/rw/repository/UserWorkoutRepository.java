package fitness.tracker.rw.repository;

import fitness.tracker.rw.model.UserWorkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWorkoutRepository extends JpaRepository<UserWorkout, Long> {
    List<UserWorkout> findByUser_FullName(String fullName);
    List<UserWorkout> findByUser_UserId(Long userId);
    List<UserWorkout> findByWorkout_WorkoutName(String workoutName);

    // ← remove your custom @Query(…) here entirely
}
