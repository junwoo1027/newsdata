package sample.newsdata.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiUser {

    private Long id;

    public ApiUser(Long id) {
        this.id = id;
    }

    public User toUser() {
        return new User(this.id);
    }

}
