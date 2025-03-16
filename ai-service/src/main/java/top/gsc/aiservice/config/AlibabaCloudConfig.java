package top.gsc.aiservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AlibabaCloudConfig {

    @Value("${alibaba.cloud.access-key-id}")
    private static String accessKeyId;

    @Value("${alibaba.cloud.access-key-secret}")
    private static String accessKeySecret;

    public static String getAccessKeyId() {
        return accessKeyId;
    }

    public static String getAccessKeySecret() {
        return accessKeySecret;
    }
}
