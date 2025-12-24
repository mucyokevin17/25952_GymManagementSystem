package fitness.tracker.rw.repository;

import fitness.tracker.rw.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    Optional<Program> findByProgramName(String programName);
    List<Program> findByWorkouts_WorkoutName(String workoutName);
    List<Program> findByTrainer_Name(String trainerName);
    List<Program> findByUserPrograms_User_FullName(String fullName);
}