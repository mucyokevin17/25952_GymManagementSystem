package fitness.tracker.rw.service;

import fitness.tracker.rw.dto.TrainerDTO;
import fitness.tracker.rw.dto.WorkoutDTO;
import fitness.tracker.rw.model.Trainer;
import fitness.tracker.rw.model.Workout;
import fitness.tracker.rw.repository.WorkoutRepository;
import fitness.tracker.rw.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkoutService {
    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    // Convert Workout entity to WorkoutDTO including TrainerDTO
    public WorkoutDTO convertToDTO(Workout workout) {
        Trainer trainer = workout.getTrainer();
        TrainerDTO trainerDTO = null;

        if (trainer != null) {
            trainerDTO = mapTrainerToDTO(trainer);
        }

        return new WorkoutDTO(
            workout.getWorkoutId(),
            workout.getWorkoutName(),
            workout.getCategory(),
            workout.getDuration(),
            trainerDTO
        );
    }

    // Convert WorkoutDTO to Workout entity
    public Workout convertToEntity(WorkoutDTO workoutDTO) {
        Workout workout = new Workout();
        workout.setWorkoutId(workoutDTO.getWorkoutId());
        workout.setWorkoutName(workoutDTO.getWorkoutName());
        workout.setCategory(workoutDTO.getCategory());
        workout.setDuration(workoutDTO.getDuration());

        if (workoutDTO.getTrainerId() != null) {
            Trainer trainer = trainerRepository.findById(workoutDTO.getTrainerId())
                    .orElseThrow(() -> new RuntimeException("Trainer not found"));
            workout.setTrainer(trainer);
        }

        return workout;
    }

    // Helper: Convert Trainer to TrainerDTO
    private TrainerDTO mapTrainerToDTO(Trainer trainer) {
        return new TrainerDTO(
            trainer.getId(),
            trainer.getName(),
            trainer.getCertification()
        );
    }

    // Get all workouts
    public List<WorkoutDTO> getAllWorkoutDTOs() {
        return workoutRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get workout by ID
    public Optional<WorkoutDTO> getWorkoutDTOById(Long workoutId) {
        return workoutRepository.findById(workoutId)
                .map(this::convertToDTO);
    }

    // Get workout by name
    public Optional<WorkoutDTO> getWorkoutDTOByName(String workoutName) {
        return workoutRepository.findByWorkoutName(workoutName)
                .map(this::convertToDTO);
    }

    // Save workout
    public WorkoutDTO saveWorkout(WorkoutDTO workoutDTO) {
        Workout workout = convertToEntity(workoutDTO);
        Workout savedWorkout = workoutRepository.save(workout);
        return convertToDTO(savedWorkout);
    }

    // Update workout
    public Optional<WorkoutDTO> updateWorkout(Long workoutId, WorkoutDTO workoutDTO) {
        return workoutRepository.findById(workoutId).map(existingWorkout -> {
            existingWorkout.setWorkoutName(workoutDTO.getWorkoutName());
            existingWorkout.setCategory(workoutDTO.getCategory());
            existingWorkout.setDuration(workoutDTO.getDuration());

            if (workoutDTO.getTrainerId() != null) {
                Trainer trainer = trainerRepository.findById(workoutDTO.getTrainerId())
                        .orElseThrow(() -> new RuntimeException("Trainer not found"));
                existingWorkout.setTrainer(trainer);
            } else {
                existingWorkout.setTrainer(null);
            }

            Workout updatedWorkout = workoutRepository.save(existingWorkout);
            return convertToDTO(updatedWorkout);
        });
    }

    // Delete workout
    public void deleteWorkout(Long workoutId) {
        workoutRepository.deleteById(workoutId);
    }

    // Get workouts by user
    public List<WorkoutDTO> getWorkoutDTOsByUser(String fullName) {
        return workoutRepository.findByUserWorkouts_User_FullName(fullName)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get workouts by trainer
    public List<WorkoutDTO> getWorkoutDTOsByTrainer(String trainerName) {
        return workoutRepository.findByTrainer_Name(trainerName)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get workouts by program
    public List<WorkoutDTO> getWorkoutDTOsByProgram(String programName) {
        return workoutRepository.findByPrograms_ProgramName(programName)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
