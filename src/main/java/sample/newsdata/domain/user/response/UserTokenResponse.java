package sample.newsdata.domain.user.response;

import sample.newsdata.domain.user.UserToken;

import java.time.LocalDateTime;

public record UserTokenResponse(
        Long id,
        String accessToken,
        LocalDateTime accessExpiredAt,
        String refreshToken,
        LocalDateTime refreshExpiredAt
) {
    public static UserTokenResponse of(UserToken userToken) {
        return new UserTokenResponse(
                userToken.getId(),
                userToken.getAccessToken(),
                userToken.getAccessExpiredAt(),
                userToken.getRefreshToken(),
                userToken.getRefreshExpiredAt()
        );
    }
}
