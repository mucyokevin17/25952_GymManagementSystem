package fitness.tracker.rw.controller;

import fitness.tracker.rw.dto.TrainerDTO;
import fitness.tracker.rw.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trainers")
public class TrainerController {
    @Autowired
    private TrainerService trainerService;

    // Get all trainers as DTOs
    @GetMapping
    public List<TrainerDTO> getAllTrainers() {
        return trainerService.getAllTrainerDTOs();
    }

    // Get trainer by ID as DTO
    @GetMapping("/{trainerId}")
    public ResponseEntity<TrainerDTO> getTrainerById(@PathVariable Long trainerId) {
        Optional<TrainerDTO> trainerDto = trainerService.getTrainerDTOById(trainerId);
        return trainerDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get trainer by name as DTO
    @GetMapping("/name/{name}")
    public ResponseEntity<TrainerDTO> getTrainerByName(@PathVariable String name) {
        Optional<TrainerDTO> trainerDto = trainerService.getTrainerDTOByName(name);
        return trainerDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a trainer using DTO
    @PostMapping
    public ResponseEntity<TrainerDTO> createTrainer(@RequestBody TrainerDTO trainerDTO) {
        return ResponseEntity.ok(trainerService.saveTrainer(trainerDTO));
    }

    // Update a trainer by ID (support both PATCH and PUT)
    @PatchMapping("/{trainerId}")
    public ResponseEntity<TrainerDTO> updateTrainer(@PathVariable Long trainerId, @RequestBody TrainerDTO trainerDTO) {
        trainerDTO.setId(trainerId);
        return ResponseEntity.ok(trainerService.saveTrainer(trainerDTO));
    }

    // Delete a trainer by ID
    @DeleteMapping("/{trainerId}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable Long trainerId) {
        trainerService.deleteTrainer(trainerId);
        return ResponseEntity.noContent().build();
    }

    // Get trainers by user name as DTOs
    @GetMapping("/user/{userName}")
    public List<TrainerDTO> getTrainersByUser(@PathVariable String userName) {
        return trainerService.getTrainerDTOsByUser(userName);
    }

    // Get trainers by program name as DTOs
    @GetMapping("/program/{programName}")
    public List<TrainerDTO> getTrainersByProgram(@PathVariable String programName) {
        return trainerService.getTrainerDTOsByProgram(programName);
    }

    // Get trainers by workout name as DTOs
    @GetMapping("/workout/{workoutName}")
    public List<TrainerDTO> getTrainersByWorkout(@PathVariable String workoutName) {
        return trainerService.getTrainerDTOsByWorkout(workoutName);
    }
}
