package top.gsc.ossservice.controller;

import com.alibaba.nacos.api.model.v2.Result;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.gsc.ossservice.service.OssService;

@RestController
@AllArgsConstructor
@RefreshScope
public class OssController {
    private OssService ossService;
    @PostMapping(value="/upload/img")
    @Operation(summary="图片上传")
    public Result<String> upload(@RequestBody MultipartFile file){
        return Result.success(ossService.upload(file));}
}
