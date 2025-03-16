package top.gsc.shareservice.controller;

import jakarta.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.gsc.shareservice.openfeign.PythonService;

@RestController
public class WordcloudController {
    @Resource
    private PythonService pythonService;

    @PostMapping("/generate_wordcloud")
    public ResponseEntity<byte[]> generateWordcloud(@RequestBody String text) {
        if (text == null || text.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        byte[] wordcloudImage = pythonService.generateWordcloud(text);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG); // 假设返回的词云图像是 PNG 格式

        return new ResponseEntity<>(wordcloudImage, headers, HttpStatus.OK);
    }
}
