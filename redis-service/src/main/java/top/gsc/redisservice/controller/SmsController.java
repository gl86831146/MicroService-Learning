package top.gsc.redisservice.controller;

import com.alibaba.nacos.api.model.v2.Result;
import lombok.AllArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.gsc.redisservice.service.SmsService;

@RestController
@RequestMapping("/sms")
@RefreshScope
@AllArgsConstructor
public class SmsController {
    private SmsService smsService;
    @PostMapping("/send")
    public Result<Object> sendSms(@RequestParam("phone") String phone){
        smsService.sendSms(phone);
        return Result.success();
    }
}
