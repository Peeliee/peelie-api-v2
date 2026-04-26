package kr.higu.peelie.user.infra;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JwtExecutorImplTest {

    @Test @DisplayName("성공: 임시 JWT를 생성한다")
    void createTemporaryJwt_Success() {
        long accessTokenExpireSeconds =3600L;

        JwtExecutorImpl jwtExecutor = new JwtExecutorImpl("secretKEy", accessTokenExpireSeconds, 3600L);
        Long userId =1L;

        System.out.println(jwtExecutor.issueAccessToken("usr_1DK1ygwnq8Iy2a1k"));
    }
}
