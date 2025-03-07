package top.gsc.redisservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.gsc.redisservice.config.CloopenConfig;
import top.gsc.redisservice.config.RedisCache;
import top.gsc.redisservice.config.RedisKeys;
import top.gsc.redisservice.service.SmsService;
import top.gsc.redisservice.utils.CommonUtils;

@Service
@Slf4j
@AllArgsConstructor
public class SmsServiceImpl implements SmsService {
    private final CloopenConfig cloopenConfig;
    private final RedisCache redisCache;


    @Override
    public void sendSms(String phone) {
        int code = CommonUtils.generateCode();
        redisCache.set(RedisKeys.getSmsKey(phone), code, 60);

        // 调用内部方法发送短信
        boolean result = send(phone, code);
        if (result) {
            log.info("短信发送成功，手机号：{}", phone);
        } else {
            log.error("短信发送失败，手机号：{}", phone);
        }
    }
    private boolean send(String phone, int code) {
        // 实际发送短信的逻辑，可能涉及到调用短信服务提供商的API
        // 这里需要你根据实际情况来实现
        return true; // 假设短信发送成功
    }


}
