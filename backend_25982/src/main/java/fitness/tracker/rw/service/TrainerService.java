package fitness.tracker.rw.service;

import fitness.tracker.rw.dto.TrainerDTO;
import fitness.tracker.rw.model.Trainer;
import fitness.tracker.rw.repository.TrainerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainerService {
    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Conversion from Trainer to TrainerDTO using ModelMapper
    public TrainerDTO convertToDTO(Trainer trainer) {
        return modelMapper.map(trainer, TrainerDTO.class);
    }

    // Conversion from TrainerDTO to Trainer using ModelMapper
    public Trainer convertToEntity(TrainerDTO trainerDTO) {
        return modelMapper.map(trainerDTO, Trainer.class);
    }

    // Retrieve all trainers as DTOs
    public List<TrainerDTO> getAllTrainerDTOs() {
        return trainerRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Retrieve trainer by ID as DTO
    public Optional<TrainerDTO> getTrainerDTOById(Long trainerId) {
        return trainerRepository.findById(trainerId)
                .map(this::convertToDTO);
    }

    // Retrieve trainer by name as DTO
    public Optional<TrainerDTO> getTrainerDTOByName(String name) {
        return trainerRepository.findByNameIgnoreCase(name)
                .map(this::convertToDTO);
    }

    // Save a trainer using DTO
    public TrainerDTO saveTrainer(TrainerDTO trainerDTO) {
        Trainer trainer = convertToEntity(trainerDTO);
        Trainer savedTrainer = trainerRepository.save(trainer);
        return convertToDTO(savedTrainer);
    }

    // Delete a trainer by ID
    public void deleteTrainer(Long trainerId) {
        trainerRepository.deleteById(trainerId);
    }

    // Retrieve trainers by user name as DTOs
    public List<TrainerDTO> getTrainerDTOsByUser(String userName) {
        return trainerRepository.findByUsers_FullName(userName)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Retrieve trainers by program name as DTOs
    public List<TrainerDTO> getTrainerDTOsByProgram(String programName) {
        return trainerRepository.findByPrograms_ProgramName(programName)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Retrieve trainers by workout name as DTOs
    public List<TrainerDTO> getTrainerDTOsByWorkout(String workoutName) {
        return trainerRepository.findByPrograms_Workouts_WorkoutName(workoutName)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
