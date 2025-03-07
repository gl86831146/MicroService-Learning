package top.gsc.userservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "gao")
@Data
public class GaoProperties {
    private String username;
    private String job;
}
