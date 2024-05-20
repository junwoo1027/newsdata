package sample.newsdata.api.support;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import sample.newsdata.domain.user.ApiUser;
import sample.newsdata.domain.user.UserToken;
import sample.newsdata.domain.user.UserTokenRepository;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserTokenRepository userTokenRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(ApiUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            throw new IllegalArgumentException("Invalid request");
        }

        Cookie[] cookies = (request.getCookies() != null) ? request.getCookies() : new Cookie[0];
        Cookie accessToken = Arrays.stream(cookies)
                .filter(c -> "NEWSAK".equals(c.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No cookies."));

        UserToken existAccessToken = userTokenRepository.findByAccessToken(accessToken.getValue());
        if (existAccessToken == null) {
            throw new IllegalArgumentException("No accessToken.");
        }

        if (existAccessToken.getAccessExpiredAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Expired accessToken.");
        }

        return new ApiUser(existAccessToken.getUserId());
    }

}
