package kr.higu.peelie.user.infra;

import kr.higu.client.KakaoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuthConfig {

    @Bean
    public KakaoClient kakaoClient() {
        return KakaoClient.create();
    }
}
