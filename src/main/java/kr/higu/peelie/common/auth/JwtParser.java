package kr.higu.peelie.common.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import kr.higu.peelie.common.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtParser {
    private final SecretKey signingKey;

    public JwtParser(@Value("${jwt.secret}") String secret) {
        this.signingKey = createSigningKey(secret);
    }

    public String getSubject(String token) {
        if (token == null || token.isBlank()) {
            throw new UnauthorizedException("jwt token is empty");
        }

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            throw new UnauthorizedException("invalid jwt token");
        }
    }

    private SecretKey createSigningKey(String secret) {
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("jwt secret is required");
        }
        try {
            return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        } catch (RuntimeException e) {
            throw new IllegalStateException("invalid jwt secret", e);
        }
    }
}
