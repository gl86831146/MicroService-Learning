package top.gsc.shareservice.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.gsc.shareservice.model.entity.User;

@FeignClient(value = "user-service")
public interface UserService {
    @GetMapping(value = "/user")
    User getUser(@RequestParam Long id);
}
