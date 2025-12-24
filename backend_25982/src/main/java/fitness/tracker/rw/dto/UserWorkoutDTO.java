package fitness.tracker.rw.dto;

import java.time.LocalDate;

public class UserWorkoutDTO {
    private Long id;
    private UserDTO user;
    private WorkoutDTO workout;
    private LocalDate datePerformed;
    private int durationCompleted;
    private String notes;

    public UserWorkoutDTO() {}

    public UserWorkoutDTO(Long id, UserDTO user, WorkoutDTO workout,
                          LocalDate datePerformed, int durationCompleted, String notes) {
        this.id = id;
        this.user = user;
        this.workout = workout;
        this.datePerformed = datePerformed;
        this.durationCompleted = durationCompleted;
        this.notes = notes;
    }

    // getters & setters ...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }

    public WorkoutDTO getWorkout() { return workout; }
    public void setWorkout(WorkoutDTO workout) { this.workout = workout; }

    public LocalDate getDatePerformed() { return datePerformed; }
    public void setDatePerformed(LocalDate datePerformed) { this.datePerformed = datePerformed; }

    public int getDurationCompleted() { return durationCompleted; }
    public void setDurationCompleted(int durationCompleted) { this.durationCompleted = durationCompleted; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
