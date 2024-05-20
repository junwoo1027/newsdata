package sample.newsdata.api.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sample.newsdata.api.ApiResponse;
import sample.newsdata.api.service.user.OauthService;
import sample.newsdata.api.service.user.UserService;
import sample.newsdata.domain.user.User;
import sample.newsdata.domain.user.response.UserTokenResponse;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final OauthService oauthService;
    private final UserService userService;

    @GetMapping("/signin/callback")
    public ApiResponse<UserTokenResponse> oauthCallback(@RequestParam("code") String code) {
        LocalDateTime createdDatetime = LocalDateTime.now();
        User user = this.oauthService.fetchUserInfo(code);
        return ApiResponse.ok(this.userService.signIn(user, createdDatetime));
    }

}

