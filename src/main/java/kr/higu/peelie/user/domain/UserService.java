package kr.higu.peelie.user.domain;

import kr.higu.peelie.user.domain.oauth.Provider;

public interface UserService {
    LoginResult loginWithCode(Provider provider, String authorizationCode);
    LoginResult loginWithAccessToken(Provider provider, String providerAccessToken);

    UserInfo getUser(String userPublicId);
    UserInfo updateUser(String userPublicId, String name, PersonalityType personalityType);

    UserInfo completeOnboarding(String userPublicId, String name, PersonalityType personalityType);
}
