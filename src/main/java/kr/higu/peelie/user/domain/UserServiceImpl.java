package kr.higu.peelie.user.domain;

import kr.higu.peelie.user.domain.oauth.OAuthClient;
import kr.higu.peelie.user.domain.oauth.OAuthProfile;
import kr.higu.peelie.user.domain.oauth.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserReader userReader;
    private final UserStore userStore;
    private final OAuthClient oauthClient;
    private final JwtExecutor jwtExecutor;

    @Override
    @Transactional
    public LoginResult loginWithCode(Provider provider, String authorizationCode) {
        String providerAccessToken = oauthClient.getAccessToken(authorizationCode);
        return login(provider, providerAccessToken);
    }

    @Override
    @Transactional
    public LoginResult loginWithAccessToken(Provider provider, String providerAccessToken) {
        return login(provider, providerAccessToken);
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfo getUser(String userPublicId) {
        User user = userReader.getUser(userPublicId);
        UserInfo userInfo = new UserInfo(user);
        return userInfo;
    }

    @Override
    @Transactional
    public UserInfo updateUser(String userPublicId, String name, PersonalityType personalityType) {
        User user = userReader.getUser(userPublicId);
        user.changeName(name);
        user.changePersonalityType(personalityType);
        UserInfo userInfo = new UserInfo(user);
        return userInfo;
    }

    @Override
    @Transactional
    public UserInfo completeOnboarding(String userPublicId, String name, PersonalityType personalityType) {
        User user = userReader.getUser(userPublicId);
        user.changeName(name);
        user.changePersonalityType(personalityType);
        user.completeOnboarding();

        UserInfo userInfo = new UserInfo(user);
        return userInfo;
    }

    private LoginResult login(Provider provider, String providerAccessToken) {
        OAuthProfile profile = oauthClient.getUserProfile(providerAccessToken);
        User user = getOrCreateUser(provider, profile.getOid(), profile.getEmail());

        return new LoginResult(
                jwtExecutor.issueAccessToken(user.getUserPublicId()),
                jwtExecutor.issueRefreshToken(user.getUserPublicId())
        );
    }

    private User getOrCreateUser(Provider provider, String oid, String email) {
        User foundUser = userReader.findUser(provider, oid);

        if (foundUser != null) {
            return foundUser;
        }

        return userStore.store(new User(provider, oid, email));
    }
}
