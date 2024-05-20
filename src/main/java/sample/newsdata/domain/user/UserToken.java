package sample.newsdata.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accessToken;

    private LocalDateTime accessExpiredAt;

    private String refreshToken;

    private LocalDateTime refreshExpiredAt;

    private Long userId;

    public UserToken(String accessToken, LocalDateTime accessExpiredAt, String refreshToken, LocalDateTime refreshExpiredAt, Long userId) {
        this.accessToken = accessToken;
        this.accessExpiredAt = accessExpiredAt;
        this.refreshToken = refreshToken;
        this.refreshExpiredAt = refreshExpiredAt;
        this.userId = userId;
    }

    public static UserToken createToken(LocalDateTime createdDateTime, Long userId) {
        final String ACCESS_TOKEN_PREFIX = "_AK_";
        final String REFRESH_TOKEN_PREFIX = "_RK_";

        String formattedDate = formatDate(createdDateTime);

        String accessToken = formattedDate + ACCESS_TOKEN_PREFIX + generateUUID();
        LocalDateTime accessExpiredAt = createdDateTime.plusDays(1);
        String refreshToken = formattedDate + REFRESH_TOKEN_PREFIX + generateUUID();
        LocalDateTime refreshExpiredAt = createdDateTime.plusDays(3);
        return new UserToken(accessToken, accessExpiredAt, refreshToken, refreshExpiredAt, userId);
    }

    private static String formatDate(LocalDateTime dateTime) {
        final String DATE_PATTERN = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return dateTime.format(formatter);
    }

    private static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
