package top.gsc.userservice.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import top.gsc.userservice.Mapper.UserMapper;
import top.gsc.userservice.entity.User;
import top.gsc.userservice.result.ErrorCode;
import top.gsc.userservice.result.Result;

@Slf4j
@RestController
@RefreshScope
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Resource
    private RestTemplate restTemplate;

    @Value("${gao.serviceFlag}")
    private boolean serviceFlag;

    //@GetMapping("/user")
    //public String user(@RequestParam String username,@RequestParam String question) {
    //    String apiserviceUrl = "http://localhost:8084/ask?question="+question ;
    //    String myserviceUrl = "http://localhost:3000";
    //    String apiInfo = restTemplate.getForObject(apiserviceUrl, String.class);
    //    String myInfo = restTemplate.getForObject(myserviceUrl, String.class);
    //    return "提问人：" + username+ "\n\n\n\n" + "回答："+apiInfo +"\n\n\n"+" 我的服务："+myInfo;
    //}

    @GetMapping("/user")
    public Result<User> getUserById(@RequestParam Long id) {
        log.info("用户服务被调用");

        if (!serviceFlag) {
            return Result.error(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), "用户服务正在维护中，请稍后。。.");
        }

        User user = userMapper.selectById(id);

        if (user != null) {
            return Result.ok(user);
        } else {
            return Result.error(ErrorCode.NOT_FOUND.getCode(),  ErrorCode.USER_NOT_EXIST.getMsg());
        }
    }
}
