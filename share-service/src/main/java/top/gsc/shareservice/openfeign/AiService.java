package top.gsc.shareservice.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "api-service")
public interface AiService {
    @PostMapping(value = "/ocr/recognize", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String recognizeText(@RequestPart("image") MultipartFile image);
}
