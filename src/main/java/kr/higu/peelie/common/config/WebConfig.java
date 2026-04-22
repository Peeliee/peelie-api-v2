package kr.higu.peelie.common.config;

import kr.higu.peelie.common.auth.AuthInterceptor;
import kr.higu.peelie.common.interceptor.CommonHttpRequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final CommonHttpRequestInterceptor commonHttpRequestInterceptor;
    private final AuthInterceptor authInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:5173",
                        "http://127.0.0.1:5173",
                        "https://peelie.higu.kr"
                )
                .allowedMethods("*")
                .allowedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonHttpRequestInterceptor)
                .order(1)
                .addPathPatterns("/**");

        registry.addInterceptor(authInterceptor)
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/api/v1/users/oauth/**",
                        "/admin/**",
                        "/v3/api-docs/**",
                        "/docs",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/favicon.ico",
                        "/error",
                        "/"
                );
    }
}
