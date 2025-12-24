package fitness.tracker.rw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "fitness.tracker.rw.repository")
@EntityScan(basePackages = "fitness.tracker.rw.model")
public class FitnessTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitnessTrackerApplication.class, args);
	}

}
