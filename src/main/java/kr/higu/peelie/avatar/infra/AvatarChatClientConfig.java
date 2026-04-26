package kr.higu.peelie.avatar.infra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AvatarChatClientConfig {

    @Bean
    public RestClient avatarFastApiRestClient(
            RestClient.Builder builder,
            @Value("${integration.avatar.fastapi.base-url:http://localhost:8001}") String baseUrl
    ) {
        return builder.baseUrl(baseUrl).build();
    }
}
