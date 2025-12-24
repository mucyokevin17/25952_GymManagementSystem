package fitness.tracker.rw.dto;

public class WorkoutDTO {
    private Long workoutId;
    private String workoutName;
    private String category;
    private int duration;
    private Long trainerId;    // if you still need it
    private TrainerDTO trainer; // full trainer info

    public WorkoutDTO() {}

    // your existing constructors
    public WorkoutDTO(Long workoutId, String workoutName, String category, int duration, Long trainerId) {
        this.workoutId = workoutId;
        this.workoutName = workoutName;
        this.category = category;
        this.duration = duration;
        this.trainerId = trainerId;
    }

    public WorkoutDTO(Long workoutId, String workoutName, String category, int duration, TrainerDTO trainer) {
        this.workoutId = workoutId;
        this.workoutName = workoutName;
        this.category = category;
        this.duration = duration;
        this.trainer = trainer;
        this.trainerId = trainer != null ? trainer.getId() : null;
    }

    // **NEW**: allow id + name only, for embedding inside UserWorkoutDTO
    public WorkoutDTO(Long workoutId, String workoutName) {
        this.workoutId = workoutId;
        this.workoutName = workoutName;
    }

    // getters & setters...
    public Long getWorkoutId() { return workoutId; }
    public void setWorkoutId(Long workoutId) { this.workoutId = workoutId; }

    public String getWorkoutName() { return workoutName; }
    public void setWorkoutName(String workoutName) { this.workoutName = workoutName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public Long getTrainerId() { return trainerId; }
    public void setTrainerId(Long trainerId) { this.trainerId = trainerId; }

    public TrainerDTO getTrainer() { return trainer; }
    public void setTrainer(TrainerDTO trainer) { this.trainer = trainer; }
}
