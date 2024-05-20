package sample.newsdata.api.service.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sample.newsdata.IntegrationTestSupport;
import sample.newsdata.domain.user.User;
import sample.newsdata.domain.user.UserRepository;
import sample.newsdata.domain.user.UserTokenRepository;
import sample.newsdata.domain.user.response.UserTokenResponse;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest extends IntegrationTestSupport {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private UserService userService;

    @AfterEach
    void clear() {
        userTokenRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @DisplayName("등록된 유저가 없을 때 회원가입 후 토큰을 발급한다.")
    @Test
    void signInAndSignUp() {
        // given
        LocalDateTime createdDateTime = LocalDateTime.now();
        User user = new User("junwoo", "junwoo1027@naver.com");

        // when
        UserTokenResponse result = userService.signIn(user, createdDateTime);

        // then
        List<User> users = userRepository.findAll();

        assertThat(users).hasSize(1);
        assertThat(users.get(0).getUsername()).isEqualTo(user.getUsername());
        assertThat(users.get(0).getEmail()).isEqualTo(user.getEmail());

        assertThat(result.id()).isNotNull();
        assertThat(result.accessToken()).isNotNull();
        assertThat(result.accessExpiredAt()).isEqualTo(createdDateTime.plusDays(1));
        assertThat(result.refreshToken()).isNotNull();
        assertThat(result.refreshExpiredAt()).isEqualTo(createdDateTime.plusDays(3));
    }

    @DisplayName("등록된 유저가 있을 때 토큰을 발급한다.")
    @Test
    void signIn() {
        // given
        LocalDateTime createdDateTime = LocalDateTime.now();
        User user = new User("junwoo", "junwoo1027@naver.com");
        userRepository.save(user);

        userRepository.save(new User("test", "test@naver.com"));

        // when
        UserTokenResponse result = userService.signIn(user, createdDateTime);

        // then
        List<User> users = userRepository.findAll();

        assertThat(users).hasSize(2);
        assertThat(result.id()).isNotNull();
        assertThat(result.accessToken()).isNotNull();
        assertThat(result.accessExpiredAt()).isEqualTo(createdDateTime.plusDays(1));
        assertThat(result.refreshToken()).isNotNull();
        assertThat(result.refreshExpiredAt()).isEqualTo(createdDateTime.plusDays(3));
    }

}