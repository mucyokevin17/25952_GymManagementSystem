package fitness.tracker.rw.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String programName;

    private String description; // ✅ New column for program description

    // Relationships
    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserProgram> userPrograms = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
        name = "program_workouts",
        joinColumns = @JoinColumn(name = "program_id"),
        inverseJoinColumns = @JoinColumn(name = "workout_id")
    )
    private List<Workout> workouts = new ArrayList<>();

    // Helper method to manage bidirectional relationship
    public void addWorkout(Workout workout) {
        workouts.add(workout);
        workout.getPrograms().add(this);
    }

    public void removeWorkout(Workout workout) {
        workouts.remove(workout);
        workout.getPrograms().remove(this);
    }
}
