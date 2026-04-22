package kr.higu.peelie.user.domain;

import lombok.Getter;

@Getter
public class LoginResult {
    private final String accessToken;
    private final String refreshToken;

    public LoginResult(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
