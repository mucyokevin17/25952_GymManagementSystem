package fitness.tracker.rw.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "user_workouts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWorkout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id")
    private Workout workout;

    private LocalDate datePerformed;
    private int durationCompleted;
    private String notes;
}