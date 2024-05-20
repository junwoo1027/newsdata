package sample.newsdata.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class UserTokenTest {


    @DisplayName("생성날짜와 유저아이디를 받아 토큰을 생성한다.")
    @Test
    void createToken() {
        // given
        LocalDateTime createdDateTime = LocalDateTime.now();

        // when
        UserToken result = UserToken.createToken(createdDateTime, 1L);

        // then
        assertThat(result.getAccessToken()).isNotNull();
        assertThat(result.getAccessExpiredAt()).isEqualTo(createdDateTime.plusDays(1));
        assertThat(result.getRefreshToken()).isNotNull();
        assertThat(result.getRefreshExpiredAt()).isEqualTo(createdDateTime.plusDays(3));
        assertThat(result.getUserId()).isEqualTo(1L);
    }

}