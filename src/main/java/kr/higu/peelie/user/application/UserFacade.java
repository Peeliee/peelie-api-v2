package kr.higu.peelie.user.application;

import kr.higu.peelie.user.domain.LoginResult;
import kr.higu.peelie.user.domain.User;
import kr.higu.peelie.user.domain.UserInfo;
import kr.higu.peelie.user.domain.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public LoginResult webLogin(String providerName, String authorizationCode) {
        return userService.loginWithCode(User.Provider.from(providerName), authorizationCode);
    }

    public LoginResult nativeLogin(String providerName, String providerAccessToken) {
        return userService.loginWithAccessToken(User.Provider.from(providerName), providerAccessToken);
    }

    public UserInfo getUser(String userPublicId) {
        return userService.getUser(userPublicId);
    }

    public void applyOnboarding(String userPublicId, String nickname) {
        userService.applyOnboarding(userPublicId, nickname);
    }

    public void changeNickname(String userPublicId, String nickname) {
        userService.changeNickname(userPublicId, nickname);
    }
}
