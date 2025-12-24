package fitness.tracker.rw.dto;

public class UserDTO {

    private Long userId;
    private String fullName;
    private String email;
    private double height;
    private double weight;
    private String goal;
    private TrainerDTO trainer;
    private Long trainerId; // Added for frontend convenience
    private Long locationId;
    private String locationName;
    private String locationHierarchy;

    // No-arg constructor
    public UserDTO() {
    }

    // Constructor without trainer
    public UserDTO(Long userId, String fullName, String email, double height, double weight, String goal) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.height = height;
        this.weight = weight;
        this.goal = goal;
    }

    // Constructor with trainer
    public UserDTO(Long userId, String fullName, String email, double height, double weight, String goal,
            TrainerDTO trainer) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.height = height;
        this.weight = weight;
        this.goal = goal;
        this.trainer = trainer;
    }

    // Full constructor
    public UserDTO(Long userId, String fullName, String email, double height, double weight, String goal,
            TrainerDTO trainer, Long locationId, String locationName, String locationHierarchy) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.height = height;
        this.weight = weight;
        this.goal = goal;
        this.goal = goal;
        this.trainer = trainer;
        this.trainerId = (trainer != null) ? trainer.getId() : null;
        this.locationId = locationId;
        this.locationName = locationName;
        this.locationHierarchy = locationHierarchy;
    }

    // Getters and setters
    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public TrainerDTO getTrainer() {
        return trainer;
    }

    public void setTrainer(TrainerDTO trainer) {
        this.trainer = trainer;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationHierarchy() {
        return locationHierarchy;
    }

    public void setLocationHierarchy(String locationHierarchy) {
        this.locationHierarchy = locationHierarchy;
    }
}
