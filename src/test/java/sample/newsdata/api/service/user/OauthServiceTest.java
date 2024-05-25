package sample.newsdata.api.service.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sample.newsdata.api.client.GitHubClient;
import sample.newsdata.api.client.GitHubUserClient;
import sample.newsdata.api.support.error.CoreApiException;
import sample.newsdata.config.OauthConfig;
import sample.newsdata.domain.user.User;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OauthServiceTest {

    @Mock
    private GitHubClient gitHubClient;

    @Mock
    private GitHubUserClient gitHubUserClient;

    @Mock
    private OauthConfig oauthConfig;

    @InjectMocks
    private OauthService oauthService;

    @DisplayName("깃허브 유저 정보를 정상 조회한다.")
    @Test
    void fetchUserInfo() {
        // given
        String code = "code";
        given(gitHubClient.getAccessToken(oauthConfig.clientId, oauthConfig.clientSecret, code))
                .willReturn("access_token=token");

        Map<String, Object> map = new HashMap<>();
        map.put("name", "junwoo");
        map.put("email", "junwoo1027@naver.com");
        given(gitHubUserClient.getUser(anyString()))
                .willReturn(map);

        // when
        User result = oauthService.fetchUserInfo(code);

        // then
        assertThat(result.getUsername()).isEqualTo("junwoo");
        assertThat(result.getEmail()).isEqualTo("junwoo1027@naver.com");
    }

    @DisplayName("깃허브 유저 정보 조회 시 유저이름이 없을 때 예외가 발생한다.")
    @Test
    void fetchUserInfoNotUsername() {
        // given
        String code = "code";
        given(gitHubClient.getAccessToken(oauthConfig.clientId, oauthConfig.clientSecret, code))
                .willReturn("access_token=token");

        Map<String, Object> map = new HashMap<>();
        given(gitHubUserClient.getUser(anyString()))
                .willReturn(map);

        // when && then
        assertThatThrownBy(() -> oauthService.fetchUserInfo(code))
                .isInstanceOf(CoreApiException.class)
                .hasMessage("Invalid user information.");
    }

    @DisplayName("깃허브 유저 정보 조회 시 이메일이 없을 때 예외가 발생한다.")
    @Test
    void fetchUserInfoNotEmail() {
        // given
        String code = "code";
        given(gitHubClient.getAccessToken(oauthConfig.clientId, oauthConfig.clientSecret, code))
                .willReturn("access_token=token");

        Map<String, Object> map = new HashMap<>();
        map.put("name", "junwoo");
        given(gitHubUserClient.getUser(anyString()))
                .willReturn(map);

        // when && then
        assertThatThrownBy(() -> oauthService.fetchUserInfo(code))
                .isInstanceOf(CoreApiException.class)
                .hasMessage("Invalid user information.");
    }

}