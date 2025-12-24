package fitness.tracker.rw.repository;

import fitness.tracker.rw.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    Optional<Trainer> findByEmail(String email);

    Optional<Trainer> findByNameIgnoreCase(String name);

    List<Trainer> findByUsers_FullName(String userName);

    List<Trainer> findByPrograms_ProgramName(String programName);

    List<Trainer> findByPrograms_Workouts_WorkoutName(String workoutName);
}