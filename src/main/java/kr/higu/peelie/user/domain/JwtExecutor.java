package kr.higu.peelie.user.domain;

public interface JwtExecutor {
    String issueAccessToken(String userPublicId);
    String issueRefreshToken(String userPublicId);
}
