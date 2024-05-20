package sample.newsdata.api.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.newsdata.api.client.GitHubClient;
import sample.newsdata.api.client.GitHubUserClient;
import sample.newsdata.config.OauthConfig;
import sample.newsdata.domain.user.User;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final GitHubClient gitHubClient;
    private final GitHubUserClient gitHubUserClient;
    private final OauthConfig oauthConfig;

    public User fetchUserInfo(String code) {
        String accessTokenResponse = gitHubClient.getAccessToken(oauthConfig.clientId, oauthConfig.clientSecret, code);
        String accessToken = parseAccessToken(accessTokenResponse);
        Map<String, Object> userInfoMap = gitHubUserClient.getUser("token " + accessToken);

        String username = Optional.ofNullable(userInfoMap.get("name")).map(Object::toString)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user information: name is required."));
        String email = Optional.ofNullable(userInfoMap.get("email")).map(Object::toString)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user information: email is required."));

        User user = new User(username, email);
        return user;
    }

    private String parseAccessToken(String response) {
        String[] pairs = response.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if ("access_token".equals(keyValue[0])) {
                return keyValue.length > 1 ? keyValue[1] : null;
            }
        }
        throw new IllegalArgumentException("Access token not found in the response.");
    }

}
