package sample.newsdata.domain.user;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sample.newsdata.IntegrationTestSupport;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class UserTokenRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private UserTokenRepository userTokenRepository;

    @DisplayName("accessToken 으로 유저토큰을 조회한다.")
    @Test
    void findByAccessToken() {
        // given
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        LocalDateTime now = LocalDateTime.now();
        userTokenRepository.save(new UserToken(accessToken, now, refreshToken, now, 1L));

        // when
        UserToken result = userTokenRepository.findByAccessToken(accessToken);

        // then
        assertThat(result.getAccessToken()).isEqualTo(accessToken);
        assertThat(result.getAccessExpiredAt()).isEqualTo(now);
        assertThat(result.getRefreshToken()).isEqualTo(refreshToken);
        assertThat(result.getRefreshExpiredAt()).isEqualTo(now);
    }

    @DisplayName("refreshToken 으로 유저토큰을 조회한다.")
    @Test
    void findByRefreshToken() {
        // given
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        LocalDateTime now = LocalDateTime.now();
        userTokenRepository.save(new UserToken(accessToken, now, refreshToken, now, 1L));

        // when
        UserToken result = userTokenRepository.findByRefreshToken(refreshToken);

        // then
        assertThat(result.getAccessToken()).isEqualTo(accessToken);
        assertThat(result.getAccessExpiredAt()).isEqualTo(now);
        assertThat(result.getRefreshToken()).isEqualTo(refreshToken);
        assertThat(result.getRefreshExpiredAt()).isEqualTo(now);
    }

}