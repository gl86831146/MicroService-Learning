package top.gsc.shareservice.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNAUTHORIZED(401, "登录失效，请重新登录"),
    FORBIDDEN(403, "没有权限，禁止访问"),
    NOT_FOUND(404,"用户未存在"),
    INTERNAL_SERVER_ERROR(500, "用户服务正在维护中，请稍后。。."),
    OPERATION_FAIL(3000,"操作失败"),
    LOGIN_STATUS_EXPIRE(3001,"登录过期"),
    CODE_SEND_FAIL(3001, "短信发送失败"),
    PARAMS_ERROR(3003,"参数异常"),
    SMS_CODE_ERROR(3002, "短信验证码错误"),
    ACCOUNT_DISABLED(3005,"账号被禁用"),
    USER_NOT_EXIST(3006,"用户不存在"),
    PHONE_IS_EXIST(3007,"手机号已被他人绑定"),
    THE_SAME_PHONE(3008,"该手机号和当前绑定手机号相同");
    private final int code;
    private final String msg;
}
