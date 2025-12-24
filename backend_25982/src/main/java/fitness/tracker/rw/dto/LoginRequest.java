package fitness.tracker.rw.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
    private String role; // Added role field for frontend compatibility
}
