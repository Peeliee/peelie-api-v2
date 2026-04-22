package kr.higu.peelie.user.infra;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import kr.higu.peelie.common.exception.InvalidParamException;
import kr.higu.peelie.user.domain.JwtExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtExecutorImpl implements JwtExecutor {
    private static final String TOKEN_TYPE_CLAIM = "tokenType";
    private static final String ACCESS_TOKEN_TYPE = "ACCESS";
    private static final String REFRESH_TOKEN_TYPE = "REFRESH";

    private final String secret;
    private final long accessTokenExpirationSeconds;
    private final long refreshTokenExpirationSeconds;

    public JwtExecutorImpl(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expiration-seconds:31536000}") long accessTokenExpirationSeconds,
            @Value("${jwt.refresh-token-expiration-seconds:2592000}") long refreshTokenExpirationSeconds
    ) {
        this.secret = secret;
        this.accessTokenExpirationSeconds = accessTokenExpirationSeconds;
        this.refreshTokenExpirationSeconds = refreshTokenExpirationSeconds;
    }

    @Override
    public String issueAccessToken(String userPublicId) {
        return issueToken(userPublicId, accessTokenExpirationSeconds, ACCESS_TOKEN_TYPE);
    }

    @Override
    public String issueRefreshToken(String userPublicId) {
        return issueToken(userPublicId, refreshTokenExpirationSeconds, REFRESH_TOKEN_TYPE);
    }

    private String issueToken(String userPublicId, long expirationSeconds, String tokenType) {
        validateUserPublicId(userPublicId);
        SecretKey key = createSigningKey();
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(expirationSeconds);

        try {
            return Jwts.builder()
                    .subject(userPublicId)
                    .claim(TOKEN_TYPE_CLAIM, tokenType)
                    .issuedAt(Date.from(now))
                    .expiration(Date.from(expiration))
                    .signWith(key)
                    .compact();
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalStateException("JWT 발급에 실패했습니다.", e);
        }
    }

    private SecretKey createSigningKey() {
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("JWT Secret가 비어있습니다.");
        }

        try {
            return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("유효하지 않은 JWT Secret입니다.", e);
        }
    }

    private void validateUserPublicId(String userPublicId) {
        if (userPublicId == null || userPublicId.isBlank()) {
            throw new InvalidParamException("사용자 Id가 비어있습니다.");
        }
    }
}
