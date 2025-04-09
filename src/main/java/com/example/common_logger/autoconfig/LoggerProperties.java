package com.example.common_logger.autoconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "common.logger")
public class LoggerProperties {

    private boolean enabled = true;

}
