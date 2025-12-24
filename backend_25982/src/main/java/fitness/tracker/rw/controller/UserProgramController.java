package fitness.tracker.rw.controller;

import fitness.tracker.rw.dto.UserProgramDTO;
import fitness.tracker.rw.service.UserProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-programs")
public class UserProgramController {

    @Autowired
    private UserProgramService userProgramService;

    // Get all user-program entries as DTOs
    @GetMapping
    public ResponseEntity<List<UserProgramDTO>> getAllUserPrograms() {
        List<UserProgramDTO> userProgramDTOs = userProgramService.getAllUserProgramDTOs();
        return userProgramDTOs.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(userProgramDTOs);
    }

    // Get user-program by ID as DTO
    @GetMapping("/{id}")
    public ResponseEntity<UserProgramDTO> getUserProgramById(@PathVariable Long id) {
        return userProgramService.getUserProgramDTOById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a user-program entry using DTO (expects UserDTO and ProgramDTO inside)
    @PostMapping
    public ResponseEntity<UserProgramDTO> createUserProgram(@RequestBody UserProgramDTO dto) {
        UserProgramDTO savedUserProgramDTO = userProgramService.saveUserProgram(dto);
        return ResponseEntity.status(201).body(savedUserProgramDTO);
    }

    // Delete a user-program entry by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserProgram(@PathVariable Long id) {
        userProgramService.deleteUserProgram(id);
        return ResponseEntity.noContent().build();
    }

    // Get user-programs by program name
    @GetMapping("/program-name/{programName}")
    public ResponseEntity<List<UserProgramDTO>> getUserProgramsByProgramName(@PathVariable String programName) {
        List<UserProgramDTO> dtos = userProgramService.getUserProgramDTOsByProgramName(programName);
        return dtos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(dtos);
    }

    // Get user-programs by user ID
    @GetMapping("/user-id/{userId}")
    public ResponseEntity<List<UserProgramDTO>> getUserProgramsByUserId(@PathVariable Long userId) {
        List<UserProgramDTO> dtos = userProgramService.getUserProgramDTOsByUserId(userId);
        return dtos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(dtos);
    }

    // Get user-programs by user full name
    @GetMapping("/user-full-name/{fullName}")
    public ResponseEntity<List<UserProgramDTO>> getUserProgramsByUserFullName(@PathVariable String fullName) {
        List<UserProgramDTO> dtos = userProgramService.getUserProgramDTOsByUserFullName(fullName);
        return dtos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(dtos);
    }
}
