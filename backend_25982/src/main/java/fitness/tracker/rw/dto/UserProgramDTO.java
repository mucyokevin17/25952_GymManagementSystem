package fitness.tracker.rw.dto;

public class UserProgramDTO {
    private Long id;
    private UserDTO user;      // UserDTO (linking User)
    private ProgramDTO program;  // ProgramDTO (linking Program)

    // No-arg constructor
    public UserProgramDTO() {}

    // Constructor with user and program
    public UserProgramDTO(Long id, UserDTO user, ProgramDTO program) {
        this.id = id;
        this.user = user;
        this.program = program;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ProgramDTO getProgram() {
        return program;
    }

    public void setProgram(ProgramDTO program) {
        this.program = program;
    }
}
