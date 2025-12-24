package fitness.tracker.rw.dto;

import java.util.List;

public class ProgramDTO {
    private Long id;
    private String programName;
    private String description; // ✅ New field
    private TrainerDTO trainer; // ✅ Full Trainer object

    public ProgramDTO() { }

    public ProgramDTO(Long id, String programName, String description, TrainerDTO trainer) {
        this.id = id;
        this.programName = programName;
        this.description = description;
        this.trainer = trainer;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TrainerDTO getTrainer() {
        return trainer;
    }

    public void setTrainer(TrainerDTO trainer) {
        this.trainer = trainer;
    }
} 
