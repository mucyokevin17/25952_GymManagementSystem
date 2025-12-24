package fitness.tracker.rw.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String fullName;
    private String email;
    private double height;
    private double weight;
    private String goal;

    // Relationships
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserProgram> userPrograms;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserWorkout> userWorkouts;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trainer_id")
    @JsonIgnoreProperties("users")
    private Trainer trainer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    @JsonIgnoreProperties("children")
    private Location location;
}