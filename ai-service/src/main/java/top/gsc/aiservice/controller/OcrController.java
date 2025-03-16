package top.gsc.aiservice.controller;

import com.aliyun.ocr_api20210707.Client;
import com.aliyun.ocr_api20210707.models.RecognizeGeneralRequest;
import com.aliyun.ocr_api20210707.models.RecognizeGeneralResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;
import com.aliyun.teautil.models.RuntimeOptions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@RestController
@RequestMapping("/ocr")
public class OcrController {

    @PostMapping("/recognize")
    public String recognizeText(@RequestParam("image") MultipartFile image) {
        try {
            // 创建阿里云 OCR 客户端
            Client client = createClient();

            // 将上传的图片转换为字节数组
            byte[] imageBytes = image.getBytes();

            // 将字节数组转换为 InputStream
            InputStream imageInputStream = new ByteArrayInputStream(imageBytes);

            // 构建通用文字识别请求
            RecognizeGeneralRequest recognizeGeneralRequest = new RecognizeGeneralRequest()
                    .setBody(imageInputStream); // 设置图片内容为 InputStream

            // 设置运行时选项
            RuntimeOptions runtime = new RuntimeOptions();

            // 调用通用文字识别接口
            RecognizeGeneralResponse response = client.recognizeGeneralWithOptions(recognizeGeneralRequest, runtime);

            // 返回识别结果
            return Common.toJSONString(response.getBody().getData());
        } catch (TeaException error) {
            // 处理阿里云 SDK 异常
            return "Error: " + error.getMessage() + ", Recommend: " + error.getData().get("Recommend");
        } catch (Exception e) {
            // 处理其他异常
            return "Error: " + e.getMessage();
        }
    }

    private Client createClient() throws Exception {
        // 直接设置 AccessKeyId 和 AccessKeySecret
        String accessKeyId = "LTAI5tF4z3tgQoWTbQFUBSux"; // 替换为你的 AccessKeyId
        String accessKeySecret = "Ecd4xNqXTltQ5EOUHg58BjwG99Mmib"; // 替换为你的 AccessKeySecret

        // 创建配置对象
        Config config = new Config()
                .setAccessKeyId(accessKeyId) // 设置 AccessKeyId
                .setAccessKeySecret(accessKeySecret) // 设置 AccessKeySecret
                .setEndpoint("ocr-api.cn-hangzhou.aliyuncs.com"); // 设置服务端点

        // 创建并返回阿里云 OCR 客户端
        return new Client(config);
    }
}
