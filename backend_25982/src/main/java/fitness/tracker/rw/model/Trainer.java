package fitness.tracker.rw.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "trainers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "users", "programs" })
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainer_id")
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String certification;

    @OneToMany(mappedBy = "trainer", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> users;

    @OneToMany(mappedBy = "trainer", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Program> programs;
}
