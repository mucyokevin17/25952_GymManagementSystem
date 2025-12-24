package fitness.tracker.rw.controller;

import fitness.tracker.rw.dto.UserWorkoutDTO;
import fitness.tracker.rw.service.UserWorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("/user-workouts")
public class UserWorkoutController {

    @Autowired
    private UserWorkoutService userWorkoutService;

    // Get all user-workout entries as DTOs
    @GetMapping
    public List<UserWorkoutDTO> getAllUserWorkouts() {
        return userWorkoutService.getAllUserWorkoutDTOs();
    }

    // Get user-workout by ID as DTO
    @GetMapping("/{id}")
    public ResponseEntity<UserWorkoutDTO> getUserWorkoutById(@PathVariable Long id) {
        return userWorkoutService.getUserWorkoutDTOById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a user-workout entry using DTO
    @PostMapping
    public ResponseEntity<UserWorkoutDTO> createUserWorkout(@RequestBody UserWorkoutDTO dto) {
        UserWorkoutDTO savedWorkout = userWorkoutService.saveUserWorkout(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWorkout); // Returns 201 Created
    }

    // Delete a user-workout entry by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserWorkout(@PathVariable Long id) {
        userWorkoutService.deleteUserWorkout(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // Get user-workouts by user full name as DTOs
    @GetMapping("/user/{fullName}")
    public ResponseEntity<List<UserWorkoutDTO>> getUserWorkoutsByUser(@PathVariable String fullName) {
        List<UserWorkoutDTO> dtos = userWorkoutService.getUserWorkoutDTOsByUser(fullName);
        return dtos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(dtos);
    }

    // Get user-workouts by workout name as DTOs
    @GetMapping("/workout/{workoutName}")
    public ResponseEntity<List<UserWorkoutDTO>> getUserWorkoutsByWorkout(@PathVariable String workoutName) {
        List<UserWorkoutDTO> dtos = userWorkoutService.getUserWorkoutDTOsByWorkout(workoutName);
        return dtos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(dtos);
    }

    // Get user-workouts by user ID
    @GetMapping("/user-id/{userId}")
    public ResponseEntity<List<UserWorkoutDTO>> getWorkoutsByUserId(@PathVariable Long userId) {
        List<UserWorkoutDTO> dtos = userWorkoutService.getUserWorkoutDTOsByUserId(userId);
        return dtos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(dtos);
    }
}
