package kr.higu.peelie.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.VirtualThreadTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean
    public AsyncTaskExecutor virtualThreadTaskExecutor() {
        return new VirtualThreadTaskExecutor("peelie-vt-");
    }
}
