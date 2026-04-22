package kr.higu.peelie.user.domain;

public interface UserService {
    LoginResult loginWithCode(User.Provider provider, String authorizationCode);
    LoginResult loginWithAccessToken(User.Provider provider, String providerAccessToken);

    UserInfo getUser(String userPublicId);

    void applyOnboarding(String userPublicId, String nickname);
    void changeNickname(String userPublicId, String nickname);
}
