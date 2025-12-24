package fitness.tracker.rw.controller;

import fitness.tracker.rw.dto.UserDTO;
import fitness.tracker.rw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Get all users as DTOs
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUserDTOs();
    }

    // Get user by ID as DTO
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        Optional<UserDTO> userDto = userService.getUserDTOById(userId);
        return userDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get user by full name as DTO
    @GetMapping("/name/{fullName}")
    public ResponseEntity<UserDTO> getUserByName(@PathVariable String fullName) {
        Optional<UserDTO> userDto = userService.getUserDTOByName(fullName);
        return userDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get user by email as DTO
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        Optional<UserDTO> userDto = userService.getUserDTOByEmail(email);
        return userDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update a user using email
    @PutMapping("/email/{email}")
    public ResponseEntity<UserDTO> updateUserByEmail(@PathVariable String email, @RequestBody UserDTO userDto) {
        Optional<UserDTO> updatedUserDto = userService.updateUserByEmail(email, userDto);
        return updatedUserDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a user using DTO
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDto) {
        return ResponseEntity.ok(userService.saveUser(userDto));
    }

    // Delete a user by ID
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    // Get users by program as DTOs
    @GetMapping("/program/{programName}")
    public List<UserDTO> getUsersByProgram(@PathVariable String programName) {
        return userService.getUserDTOsByProgram(programName);
    }

    // Get users by workout as DTOs
    @GetMapping("/workout/{workoutName}")
    public List<UserDTO> getUsersByWorkout(@PathVariable String workoutName) {
        return userService.getUserDTOsByWorkout(workoutName);
    }

    // Get users by trainer as DTOs
    @GetMapping("/trainer/{trainerName}")
    public List<UserDTO> getUsersByTrainer(@PathVariable String trainerName) {
        return userService.getUserDTOsByTrainer(trainerName);
    }
}
