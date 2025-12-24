package fitness.tracker.rw.controller;

import fitness.tracker.rw.dto.WorkoutDTO;
import fitness.tracker.rw.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    // Get all workouts
    @GetMapping
    public List<WorkoutDTO> getAllWorkouts() {
        return workoutService.getAllWorkoutDTOs();
    }

    // Get workout by ID
    @GetMapping("/{workoutId}")
    public ResponseEntity<WorkoutDTO> getWorkoutById(@PathVariable Long workoutId) {
        return workoutService.getWorkoutDTOById(workoutId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get workout by name
    @GetMapping("/name/{workoutName}")
    public ResponseEntity<WorkoutDTO> getWorkoutByName(@PathVariable String workoutName) {
        return workoutService.getWorkoutDTOByName(workoutName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new workout
    @PostMapping
    public ResponseEntity<WorkoutDTO> createWorkout(@RequestBody WorkoutDTO workoutDTO) {
        WorkoutDTO createdWorkout = workoutService.saveWorkout(workoutDTO);
        return ResponseEntity.ok(createdWorkout);
    }

    // Update a workout
    @PutMapping("/{workoutId}")
    public ResponseEntity<WorkoutDTO> updateWorkout(
            @PathVariable Long workoutId,
            @RequestBody WorkoutDTO workoutDTO) {
        return workoutService.updateWorkout(workoutId, workoutDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{workoutId}")
    public ResponseEntity<WorkoutDTO> patchWorkout(
            @PathVariable Long workoutId,
            @RequestBody WorkoutDTO workoutDTO) {
        return workoutService.updateWorkout(workoutId, workoutDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a workout
    @DeleteMapping("/{workoutId}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long workoutId) {
        workoutService.deleteWorkout(workoutId);
        return ResponseEntity.noContent().build();
    }

    // Get workouts by user name
    @GetMapping("/user/{fullName}")
    public ResponseEntity<List<WorkoutDTO>> getWorkoutsByUser(@PathVariable String fullName) {
        List<WorkoutDTO> workouts = workoutService.getWorkoutDTOsByUser(fullName);
        return workouts.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(workouts);
    }

    // Get workouts by trainer name
    @GetMapping("/trainer/{trainerName}")
    public ResponseEntity<List<WorkoutDTO>> getWorkoutsByTrainer(@PathVariable String trainerName) {
        List<WorkoutDTO> workouts = workoutService.getWorkoutDTOsByTrainer(trainerName);
        return workouts.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(workouts);
    }

    // Get workouts by program name
    @GetMapping("/program/{programName}")
    public ResponseEntity<List<WorkoutDTO>> getWorkoutsByProgram(@PathVariable String programName) {
        List<WorkoutDTO> workouts = workoutService.getWorkoutDTOsByProgram(programName);
        return workouts.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(workouts);
    }
}
