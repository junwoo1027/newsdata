package sample.newsdata.api.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.newsdata.api.service.user.UserService;
import sample.newsdata.domain.user.response.UserTokenResponse;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/api/v1/refresh")
    public ResponseEntity refreshToken(@CookieValue("NEWSRK") String refreshToken) {
        LocalDateTime now = LocalDateTime.now();
        UserTokenResponse token = userService.refreshToken(refreshToken, now);
        ResponseCookie accessCookie = createCookie("NEWSAK", token.accessToken(), now, token.accessExpiredAt());
        ResponseCookie refreshCookie = createCookie("NEWSRK", token.refreshToken(), now, token.refreshExpiredAt());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body("Cookie added.");
    }

    private static ResponseCookie createCookie(String name, String token, LocalDateTime now, LocalDateTime expiredAt) {
        ResponseCookie refreshCookie = ResponseCookie.from(name, token)
                .httpOnly(false)
                .secure(true)
                .path("/")
                .maxAge(ChronoUnit.SECONDS.between(now, expiredAt))
                .build();
        return refreshCookie;
    }

}
