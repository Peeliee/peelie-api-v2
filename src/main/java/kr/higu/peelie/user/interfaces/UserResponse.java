package kr.higu.peelie.user.interfaces;

import kr.higu.peelie.user.domain.LoginResult;
import kr.higu.peelie.user.domain.PersonalityType;
import kr.higu.peelie.user.domain.UserInfo;
import lombok.Getter;

import java.time.LocalDateTime;

public class UserResponse {

    @Getter
    public static class Login {
        private final String accessToken;
        private final String refreshToken;

        public Login(LoginResult result) {
            this.accessToken = result.getAccessToken();
            this.refreshToken = result.getRefreshToken();
        }
    }

    @Getter
    public static class User {
        private final String userPublicId;
        private final String friendCode;
        private final String name;
        private final PersonalityType personalityType;
        private final Boolean isOnboarded;
        private final LocalDateTime createdAt;

        public User(UserInfo userInfo) {
            this.userPublicId = userInfo.getUserPublicId();
            this.friendCode = userInfo.getFriendCode();
            this.name = userInfo.getName();
            this.personalityType = userInfo.getPersonalityType();
            this.isOnboarded = userInfo.getIsOnboarded();
            this.createdAt = userInfo.getCreatedAt();
        }
    }
}
