package top.gsc.userservice.controller;


import jakarta.annotation.Resource;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.gsc.userservice.config.GaoProperties;

@RestController
@RefreshScope
public class TestController {
    //@Value("${gao.username}")
    //private String username;
    //
    //@Value("${gao.job}")
    //private String job;
@Resource
private GaoProperties gaoProperties;
    @GetMapping("/test")
    public String test() {
        return "读取到配置中心的值:" +gaoProperties.getUsername() + "," + gaoProperties.getJob();
    }
}
