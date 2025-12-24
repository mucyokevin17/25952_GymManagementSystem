package fitness.tracker.rw.repository;

import fitness.tracker.rw.model.UserProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserProgramRepository extends JpaRepository<UserProgram, Long> {
    List<UserProgram> findByProgram_ProgramName(String programName);
    List<UserProgram> findByUser_UserId(Long userId);
    List<UserProgram> findByUser_FullName(String fullName);
}