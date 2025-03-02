package top.gsc.shareservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import top.gsc.shareservice.config.RandomLoadBalancerConfig;

@SpringBootApplication
@EnableFeignClients(basePackages = {"top.gsc.shareservice.openfeign"})
@LoadBalancerClient(name = "user-service",configuration = RandomLoadBalancerConfig.class)
public class ShareServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShareServiceApplication.class, args);
    }

}
