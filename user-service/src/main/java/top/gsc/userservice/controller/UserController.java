package top.gsc.userservice.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserController {
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/user")
    public String user(@RequestParam String username,@RequestParam String question) {
        String apiserviceUrl = "http://localhost:8084/ask?question="+question ;
        String myserviceUrl = "http://localhost:3000";
        String apiInfo = restTemplate.getForObject(apiserviceUrl, String.class);
        String myInfo = restTemplate.getForObject(myserviceUrl, String.class);
        return "提问人：" + username+ "\n\n\n\n" + "回答："+apiInfo +"\n\n\n"+" 我的服务："+myInfo;
    }
}
