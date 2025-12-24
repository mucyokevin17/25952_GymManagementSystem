package fitness.tracker.rw.dto;

public class TrainerDTO {
    private Long id;
    private String name;
    private String certification;

    public TrainerDTO() { }

    public TrainerDTO(Long id, String name, String certification) {
        this.id = id;
        this.name = name;
        this.certification = certification;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }
}
