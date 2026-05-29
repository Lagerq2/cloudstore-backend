package systementor.cloudstoreuserservice.model.user.dto;

public record AuthResponse(
        String token,
        String email
) {
}
