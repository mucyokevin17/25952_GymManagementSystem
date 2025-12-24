package fitness.tracker.rw.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "workouts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"trainer", "userWorkouts", "programs"})
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_id")
    private Long workoutId;

    private String workoutName;
    private String category;
    private int duration;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    @JsonBackReference
    private Trainer trainer;

    @OneToMany(mappedBy = "workout", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<UserWorkout> userWorkouts;

    @ManyToMany(mappedBy = "workouts", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Program> programs;
}
