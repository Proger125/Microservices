package edu.dzyachenka.processor.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RetryConfig {
    private static final long RETRY_TIMEOUT = 1000L;

    @Bean
    public RetryTemplate retryTemplate() {
        return RetryTemplate.builder()
                .maxAttempts(3)
                .fixedBackoff(RETRY_TIMEOUT)
                .retryOn(InterruptedException.class)
                .build();
    }
}
