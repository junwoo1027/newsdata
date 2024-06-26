package sample.newsdata.api.service.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.newsdata.api.support.error.CoreApiErrorType;
import sample.newsdata.api.support.error.CoreApiException;
import sample.newsdata.domain.user.User;
import sample.newsdata.domain.user.UserRepository;
import sample.newsdata.domain.user.UserToken;
import sample.newsdata.domain.user.UserTokenRepository;
import sample.newsdata.domain.user.response.UserTokenResponse;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;

    @Transactional
    public UserTokenResponse signIn(User user, LocalDateTime createdDateTime) {
        User findUser = userRepository.findByEmail(user.getEmail());
        User userToUse;
        if (findUser == null) {
            userToUse = userRepository.save(user);
        } else {
            userToUse = findUser;
        }
        UserToken userToken = this.createUserToken(createdDateTime, userToUse.getId());
        return UserTokenResponse.of(userToken);
    }

    @Transactional
    public UserTokenResponse refreshToken(String refreshToken, LocalDateTime createdDateTime) {
        UserToken userToken = userTokenRepository.findByRefreshToken(refreshToken);
        if(userToken == null) {
            throw new CoreApiException(CoreApiErrorType.NOT_FOUND_USER_TOKEN);
        }

        if (userToken.getRefreshExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CoreApiException(CoreApiErrorType.EXPIRED_REFRESH_TOKEN);
        }

        UserToken newToken = this.createUserToken(createdDateTime, userToken.getUserId());
        return UserTokenResponse.of(newToken);
    }

    private UserToken createUserToken(LocalDateTime createdDateTime, Long userId) {
        UserToken token = UserToken.createToken(createdDateTime, userId);
        return userTokenRepository.save(token);
    }

}
