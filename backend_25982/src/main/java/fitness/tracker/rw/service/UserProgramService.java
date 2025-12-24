package fitness.tracker.rw.service;

import fitness.tracker.rw.dto.ProgramDTO;
import fitness.tracker.rw.dto.UserDTO;
import fitness.tracker.rw.dto.UserProgramDTO;
import fitness.tracker.rw.dto.TrainerDTO;
import fitness.tracker.rw.model.Program;
import fitness.tracker.rw.model.User;
import fitness.tracker.rw.model.UserProgram;
import fitness.tracker.rw.repository.UserProgramRepository;
import fitness.tracker.rw.repository.ProgramRepository;
import fitness.tracker.rw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProgramService {

    @Autowired
    private UserProgramRepository userProgramRepository;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProgramService programService;

    @Autowired
    private UserService userService;

    // Convert UserProgram entity to UserProgramDTO
    public UserProgramDTO convertToDTO(UserProgram userProgram) {
        // Creating UserDTO from User entity
        UserDTO userDTO = new UserDTO(
            userProgram.getUser().getUserId(),
            userProgram.getUser().getFullName(),
            userProgram.getUser().getEmail(),
            userProgram.getUser().getHeight(),
            userProgram.getUser().getWeight(),
            userProgram.getUser().getGoal()
        );

        // Creating ProgramDTO from Program entity
        Program program = userProgram.getProgram();
        TrainerDTO trainerDTO = null;
        if (program != null && program.getTrainer() != null) {
            trainerDTO = new TrainerDTO(
                program.getTrainer().getId(),
                program.getTrainer().getName(),
                program.getTrainer().getCertification()
            );
        }

        ProgramDTO programDTO = new ProgramDTO(
            program.getId(),
            program.getProgramName(),
            program.getDescription(),
            trainerDTO
        );

        return new UserProgramDTO(userProgram.getId(), userDTO, programDTO);
    }

    // Convert UserProgramDTO to UserProgram entity (fetching associated Program and User)
    public UserProgram convertToEntity(UserProgramDTO dto) {
        UserProgram userProgram = new UserProgram();
        userProgram.setId(dto.getId());

        if (dto.getProgram() != null && dto.getProgram().getId() != null) {
            Program program = programRepository.findById(dto.getProgram().getId())
                .orElseThrow(() -> new RuntimeException("Program not found"));
            userProgram.setProgram(program);
        }

        if (dto.getUser() != null && dto.getUser().getUserId() != null) {
            User user = userRepository.findById(dto.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
            userProgram.setUser(user);
        }

        return userProgram;
    }

    // Retrieve all UserPrograms as DTOs
    public List<UserProgramDTO> getAllUserProgramDTOs() {
        return userProgramRepository.findAll()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    // Retrieve a UserProgram by ID as DTO
    public Optional<UserProgramDTO> getUserProgramDTOById(Long id) {
        return userProgramRepository.findById(id)
            .map(this::convertToDTO);
    }

    // Retrieve UserPrograms by program name as DTOs
    public List<UserProgramDTO> getUserProgramDTOsByProgramName(String programName) {
        return userProgramRepository.findByProgram_ProgramName(programName)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    // Retrieve UserPrograms by user ID as DTOs
    public List<UserProgramDTO> getUserProgramDTOsByUserId(Long userId) {
        return userProgramRepository.findByUser_UserId(userId)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    // Retrieve UserPrograms by user full name as DTOs
    public List<UserProgramDTO> getUserProgramDTOsByUserFullName(String fullName) {
        return userProgramRepository.findByUser_FullName(fullName)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    // Save a UserProgram using DTO
    public UserProgramDTO saveUserProgram(UserProgramDTO dto) {
        UserProgram userProgram = convertToEntity(dto);
        UserProgram saved = userProgramRepository.save(userProgram);
        return convertToDTO(saved);
    }

    // Delete a UserProgram by ID
    public void deleteUserProgram(Long id) {
        userProgramRepository.deleteById(id);
    }
}
