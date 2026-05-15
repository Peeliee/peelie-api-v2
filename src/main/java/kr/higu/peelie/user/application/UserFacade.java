package kr.higu.peelie.user.application;

import kr.higu.peelie.user.domain.*;
import kr.higu.peelie.user.domain.oauth.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public LoginResult webLogin(String providerName, String authorizationCode) {
        return userService.loginWithCode(Provider.from(providerName), authorizationCode);
    }

    public LoginResult nativeLogin(String providerName, String providerAccessToken) {
        return userService.loginWithAccessToken(Provider.from(providerName), providerAccessToken);
    }

    public UserInfo getUser(String userPublicId) {
        return userService.getUser(userPublicId);
    }

    public UserInfo updateUser(String userPublicId, String name, String personalityType) {
        return userService.updateUser(userPublicId, name, PersonalityType.from(personalityType));
    }

    public void applyOnboarding(String userPublicId, String name, String personalityType) {
        userService.applyOnboarding(userPublicId, name, PersonalityType.from(personalityType));
    }
}
