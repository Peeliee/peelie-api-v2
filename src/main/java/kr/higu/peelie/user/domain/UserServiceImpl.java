package kr.higu.peelie.user.domain;

import kr.higu.peelie.common.exception.InvalidParamException;
import kr.higu.peelie.user.domain.oauth.OAuthClient;
import kr.higu.peelie.user.domain.oauth.OAuthProfile;
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
    public LoginResult loginWithCode(User.Provider provider, String authorizationCode) {
        validateSupportedProvider(provider);
        String providerAccessToken = oauthClient.getAccessToken(authorizationCode);
        return login(provider, providerAccessToken);
    }

    @Override
    @Transactional
    public LoginResult loginWithAccessToken(User.Provider provider, String providerAccessToken) {
        return login(provider, providerAccessToken);
    }

    private LoginResult login(User.Provider provider, String providerAccessToken) {
        validateSupportedProvider(provider);
        OAuthProfile profile = oauthClient.getUserProfile(providerAccessToken);
        User user = getOrCreateUser(provider, profile.getOid(), profile.getEmail());

        return new LoginResult(
                jwtExecutor.issueAccessToken(user.getUserPublicId()),
                jwtExecutor.issueRefreshToken(user.getUserPublicId())
        );
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfo getUser(String userPublicId) {
        return new UserInfo(userReader.getUser(userPublicId));
    }

    @Override
    @Transactional
    public void applyOnboarding(String userPublicId, String nickname) {
        User user = userReader.getUser(userPublicId);
        user.completeOnboarding(nickname);
    }

    @Override
    @Transactional
    public void changeNickname(String userPublicId, String nickname) {
        User user = userReader.getUser(userPublicId);
        user.changeNickname(nickname);
    }

    private User getOrCreateUser(User.Provider provider, String oid, String email) {
        User foundUser = userReader.findUser(provider, oid);

        if (foundUser != null) {
            return foundUser;
        }

        return userStore.store(new User(provider, oid, email));
    }

    private void validateSupportedProvider(User.Provider provider) {
        if (provider != User.Provider.KAKAO) {
            throw new InvalidParamException("지원하지 않는 OAuth2.0 로그인 방식입니다.: " + provider);
        }
    }
}
