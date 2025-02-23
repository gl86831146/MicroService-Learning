package top.gsc.orderservice.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/order")
    public String order(@RequestParam String username, @RequestParam String name) {
        String userserviceUrl = "http://localhost:8081/user?username=" + username;
        String productserviceUrl = "http://localhost:8083/product?name="+ name;
        String userInfo = restTemplate.getForObject(userserviceUrl, String.class);
        String productInfo = restTemplate.getForObject(productserviceUrl, String.class);
        return "订单创建者: " + userInfo + "\n" + "订单内容: " + productInfo;
    }
}
