package fitness.tracker.rw.controller;

import fitness.tracker.rw.dto.ProgramDTO;
import fitness.tracker.rw.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/programs")
public class ProgramController {
    @Autowired
    private ProgramService programService;

    @GetMapping
    public List<ProgramDTO> getAllPrograms() {
        return programService.getAllProgramDTOs();
    }

    @GetMapping("/{programId}")
    public ResponseEntity<ProgramDTO> getProgramById(@PathVariable Long programId) {
        return programService.getProgramDTOById(programId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{programName}")
    public ResponseEntity<ProgramDTO> getProgramByName(@PathVariable String programName) {
        return programService.getProgramDTOByName(programName)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProgramDTO> createProgram(@RequestBody ProgramDTO programDTO) {
        return ResponseEntity.ok(programService.saveProgram(programDTO));
    }

    @PutMapping("/{programId}")
    public ResponseEntity<ProgramDTO> updateProgram(@PathVariable Long programId, @RequestBody ProgramDTO programDTO) {
        return programService.updateProgram(programId, programDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{programId}")
    public ResponseEntity<ProgramDTO> patchProgram(@PathVariable Long programId, @RequestBody ProgramDTO programDTO) {
        return programService.updateProgram(programId, programDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{programId}")
    public ResponseEntity<Void> deleteProgram(@PathVariable Long programId) {
        boolean deleted = programService.deleteProgram(programId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{fullName}")
    public ResponseEntity<List<ProgramDTO>> getProgramsByUser(@PathVariable String fullName) {
        List<ProgramDTO> programs = programService.getProgramDTOsByUser(fullName);
        return programs.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(programs);
    }

    @GetMapping("/trainer/{trainerName}")
    public ResponseEntity<List<ProgramDTO>> getProgramsByTrainer(@PathVariable String trainerName) {
        List<ProgramDTO> programs = programService.getProgramDTOsByTrainer(trainerName);
        return programs.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(programs);
    }
}
