package kr.higu.peelie.user.domain.oauth;

public interface OAuthClient {
    String getAccessToken(String code);
    OAuthProfile getUserProfile(String accessToken);
}
