package sample.newsdata.api.controller.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import sample.newsdata.ControllerTestSupport;
import sample.newsdata.domain.user.User;
import sample.newsdata.domain.user.response.UserTokenResponse;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OauthControllerTest extends ControllerTestSupport {

    @DisplayName("유저정보를 조회 후 토큰을 발급받는다.")
    @Test
    void oauthCallback() throws Exception {
        String code = "testCode";
        User user = new User("junwoo", "junwoo1027@naver.com");
        UserTokenResponse result = new UserTokenResponse(
                1L,
                "",
                LocalDateTime.now(),
                "",
                LocalDateTime.now());
        given(oauthService.fetchUserInfo(code)).willReturn(user);
        given(userService.signIn(any(User.class), any(LocalDateTime.class))).willReturn(result);

        mockMvc.perform(get("/signin/callback")
                        .param("code", code)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());
    }

}