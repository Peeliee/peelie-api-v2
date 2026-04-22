package kr.higu.peelie.user.interfaces;

import kr.higu.peelie.user.domain.LoginResult;
import lombok.Getter;

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
}
