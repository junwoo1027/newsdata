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

    private final OauthService oauthService;
    private final UserService userService;

    @GetMapping("/signin/callback")
    public RedirectView oauthCallback(@RequestParam("code") String code) {
        LocalDateTime createdDatetime = LocalDateTime.now();
        User user = this.oauthService.fetchUserInfo(code);
        UserTokenResponse res = this.userService.signIn(user, createdDatetime);
        String url = "http://localhost:3000/oauth?token=" + res.accessToken() +"&tokenExpAt=" + res.accessExpiredAt()
                + "&refresh=" + res.refreshToken() + "&refreshExpAt=" + res.refreshExpiredAt();
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(url);
        return redirectView;
    }

}

