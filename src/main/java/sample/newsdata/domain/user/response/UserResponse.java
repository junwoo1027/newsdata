package sample.newsdata.domain.user.response;

import sample.newsdata.domain.user.User;

public record UserResponse(
    Long id,
    String username,
    String email
) {
    public static UserResponse of(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
