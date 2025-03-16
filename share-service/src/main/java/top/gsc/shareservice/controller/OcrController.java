package top.gsc.shareservice.controller;

import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.gsc.shareservice.openfeign.AiService;

@RestController
public class OcrController {
    @Resource
    private AiService aiService;
    @PostMapping(value = "/ocr/recognize", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String recognizeText(@RequestPart("image") MultipartFile image) {
        return aiService.recognizeText(image);
    }
}
