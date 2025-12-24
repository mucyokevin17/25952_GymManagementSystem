package fitness.tracker.rw.config;

import fitness.tracker.rw.model.Trainer;
import fitness.tracker.rw.repository.TrainerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TrainerSeeder {

    @Bean
    CommandLineRunner initTrainers(TrainerRepository trainerRepository) {
        return args -> {
            seedTrainer(trainerRepository, "Alice Kanyana", "alice@example.com", "Certified Yoga Instructor");
            seedTrainer(trainerRepository, "John Doe", "john@example.com", "Strength & Conditioning Coach");
            seedTrainer(trainerRepository, "Jane Smith", "jane@example.com", "Nutrition Specialist");
        };
    }

    private void seedTrainer(TrainerRepository repository, String name, String email, String certification) {
        // Try to find by email first (robust)
        if (repository.findByEmail(email).isEmpty()) {
            // Try to find by name to update existing records adding email
            repository.findByNameIgnoreCase(name).ifPresentOrElse(
                    existing -> {
                        if (existing.getEmail() == null) {
                            existing.setEmail(email);
                            repository.save(existing);
                            System.out.println("Updated email for trainer: " + name);
                        }
                    },
                    () -> {
                        // Create new if neither exists
                        Trainer trainer = new Trainer();
                        trainer.setName(name);
                        trainer.setEmail(email);
                        trainer.setCertification(certification);
                        repository.save(trainer);
                        System.out.println("Seeded trainer: " + name);
                    });
        }
    }
}
