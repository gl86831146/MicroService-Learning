package top.gsc.shareservice.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "python-service")
public interface PythonService {
    @PostMapping(value = "/generate_wordcloud", consumes = MediaType.APPLICATION_JSON_VALUE)
    byte[] generateWordcloud(@RequestBody String text);
}
