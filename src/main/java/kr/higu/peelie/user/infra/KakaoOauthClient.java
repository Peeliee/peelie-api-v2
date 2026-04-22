package kr.higu.peelie.user.infra;

import kr.higu.client.KakaoClient;
import kr.higu.dto.kakao.KakaoTokenResponse;
import kr.higu.dto.kakao.KakaoUserResponse;
import kr.higu.exceptions.OAuthException;
import kr.higu.peelie.user.domain.exception.OAuthTokenException;
import kr.higu.peelie.user.domain.exception.OAuthUserProfileException;
import kr.higu.peelie.user.domain.oauth.OAuthClient;
import kr.higu.peelie.user.domain.oauth.OAuthProfile;
import kr.higu.request.kakao.KakaoPropertyKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
@RequiredArgsConstructor
public class KakaoOauthClient implements OAuthClient {

    private final KakaoClient kakaoClient;

    @Value("${oauth.kakao.client-id}")
    private String clientId;
    @Value("${oauth.kakao.redirect-uri}")
    private String redirectUri;
    @Value("${oauth.kakao.client-secret:}")
    private String clientSecret;

    @Override
    public String getAccessToken(String code) {
        try {
            var requestBuilder = kakaoClient.getToken()
                    .clientId(clientId)
                    .redirectUri(redirectUri)
                    .code(code);
            if (StringUtils.hasText(clientSecret)) {
                requestBuilder.clientSecret(clientSecret);
            }
            KakaoTokenResponse tokenResponse = requestBuilder.build().execute();
            log.info("access token = {}", tokenResponse.accessToken());

            return tokenResponse.accessToken();

        } catch (OAuthException e) {
            throw new OAuthTokenException(
                    "Kakao token exchange failed. provider=KAKAO, stage=token_exchange",
                    e
            );
        }
    }

    @Override
    public OAuthProfile getUserProfile(String accessToken) {
        try {
            KakaoUserResponse userResponse = kakaoClient.getUserInfo()
                    .accessToken(accessToken)
                    .secureResource(false)
                    .propertyKeys(KakaoPropertyKey.EMAIL)
                    .build()
                    .execute();

            String providerUserId = String.valueOf(userResponse.id());
            String email = userResponse.kakaoAccount() == null ? null : userResponse.kakaoAccount().email();
            return new OAuthProfile(providerUserId, email);

        } catch (OAuthException e) {
            throw new OAuthUserProfileException(
                    "Kakao user profile fetch failed. provider=KAKAO, stage=user_profile", e
            );
        }
    }
}
