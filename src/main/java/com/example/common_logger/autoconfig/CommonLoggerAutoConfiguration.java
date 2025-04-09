package com.example.common_logger.autoconfig;

import com.example.common_logger.aspect.LoggingAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example.common_logger")
@ConditionalOnProperty(name = "common.logger.enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(LoggerProperties.class)
public class CommonLoggerAutoConfiguration {

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
