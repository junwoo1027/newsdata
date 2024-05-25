package sample.newsdata.api.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import sample.newsdata.api.service.user.OauthService;
import sample.newsdata.api.service.user.UserService;
import sample.newsdata.domain.user.User;
import sample.newsdata.domain.user.response.UserTokenResponse;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
public class OauthController {

    private static final String FRONT_URL = "http://localhost:3000";
    private final OauthService oauthService;
    private final UserService userService;

    @GetMapping("/signin/callback")
    public RedirectView oauthCallback(
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "error", required = false) String error
    ) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(getRedirectUrl(code, error));
        return redirectView;
    }

    private String getRedirectUrl(String code, String error) {
        if (error != null) {
            return FRONT_URL + "/login";
        }
        return buildOAuthRedirectUrl(code);
    }

    private String buildOAuthRedirectUrl(String code) {
        LocalDateTime createdDatetime = LocalDateTime.now();
        User user = oauthService.fetchUserInfo(code);
        UserTokenResponse res = userService.signIn(user, createdDatetime);
        return String.format("%s?token=%s&tokenExpAt=%s&refresh=%s&refreshExpAt=%s",
                FRONT_URL + "/oauth",
                res.accessToken(),
                res.accessExpiredAt(),
                res.refreshToken(),
                res.refreshExpiredAt()
        );
    }
    
}

