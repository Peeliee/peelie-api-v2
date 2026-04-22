package kr.higu.peelie.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Peelie API",
                description = "**Peelie API**",
                version = "v2"
        )
)
@Configuration
public class SwaggerConfig {

        @Value("${swagger.requestUrl}")
        private String requestUrl;

        @Bean
        public OpenAPI openAPI() {

                Server server = new Server();
                server.setUrl(requestUrl);
                server.setDescription("현재 환경 서버");

                String jwtSchemeName = "jwt토큰 설정";
                SecurityScheme securityScheme = new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT");

                SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

                return new OpenAPI()
                        .components(new Components().addSecuritySchemes(jwtSchemeName, securityScheme))
                        .addSecurityItem(securityRequirement)
                        .addServersItem(server);
        }
}
