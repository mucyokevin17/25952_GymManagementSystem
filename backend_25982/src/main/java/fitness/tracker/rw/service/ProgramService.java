package fitness.tracker.rw.service;

import fitness.tracker.rw.dto.ProgramDTO;
import fitness.tracker.rw.dto.TrainerDTO;
import fitness.tracker.rw.model.Program;
import fitness.tracker.rw.model.Trainer;
import fitness.tracker.rw.repository.ProgramRepository;
import fitness.tracker.rw.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgramService {
    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    public ProgramDTO convertToDTO(Program program) {
        Trainer trainer = program.getTrainer();
        TrainerDTO trainerDTO = trainer != null
                ? new TrainerDTO(trainer.getId(), trainer.getName(), trainer.getCertification())
                : null;

        return new ProgramDTO(
                program.getId(),
                program.getProgramName(),
                program.getDescription(),
                trainerDTO
        );
    }

    public Program convertToEntity(ProgramDTO dto) {
        Program program = new Program();
        program.setId(dto.getId());
        program.setProgramName(dto.getProgramName());
        program.setDescription(dto.getDescription());

        if (dto.getTrainer() != null && dto.getTrainer().getId() != null) {
            Trainer trainer = trainerRepository.findById(dto.getTrainer().getId())
                    .orElseThrow(() -> new RuntimeException("Trainer not found"));
            program.setTrainer(trainer);
        }

        return program;
    }

    public ProgramDTO saveProgram(ProgramDTO dto) {
        Program program = convertToEntity(dto);
        return convertToDTO(programRepository.save(program));
    }

    public List<ProgramDTO> getAllProgramDTOs() {
        return programRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProgramDTO> getProgramDTOById(Long id) {
        return programRepository.findById(id)
                .map(this::convertToDTO);
    }

    public Optional<ProgramDTO> getProgramDTOByName(String name) {
        return programRepository.findByProgramName(name)
                .map(this::convertToDTO);
    }

    public Optional<ProgramDTO> updateProgram(Long id, ProgramDTO dto) {
        return programRepository.findById(id).map(existing -> {
            existing.setProgramName(dto.getProgramName());
            existing.setDescription(dto.getDescription());

            if (dto.getTrainer() != null && dto.getTrainer().getId() != null) {
                Trainer trainer = trainerRepository.findById(dto.getTrainer().getId())
                        .orElseThrow(() -> new RuntimeException("Trainer not found"));
                existing.setTrainer(trainer);
            }

            return convertToDTO(programRepository.save(existing));
        });
    }

    public boolean deleteProgram(Long id) {
        if (programRepository.existsById(id)) {
            programRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<ProgramDTO> getProgramDTOsByUser(String fullName) {
        return programRepository.findByUserPrograms_User_FullName(fullName)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProgramDTO> getProgramDTOsByTrainer(String trainerName) {
        return programRepository.findByTrainer_Name(trainerName)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
