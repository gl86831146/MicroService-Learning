package top.gsc.aiservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.gsc.aiservice.config.DeepSeekConfig;

import java.util.ArrayList;
import java.util.List;

@RestController
@RefreshScope
public class AiController {

    @Autowired
    private DeepSeekConfig deepSeekConfig;

    @PostMapping("/ask")
    public ResponseEntity<String> askQuestion(@RequestParam String question) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String apiUrl = deepSeekConfig.getApiUrl() + "/v1/chat/completions"; // 确保路径正确
            HttpPost request = new HttpPost(apiUrl);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Authorization", "Bearer " + deepSeekConfig.getApiKey());

            // 构建请求体
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(new ModelRequest(
                    "deepseek-chat", // 模型名称
                    question,
                    100, // max_tokens
                    0.7 // temperature
            ));
            request.setEntity(new StringEntity(requestBody));

            System.out.println("Request URL: " + apiUrl);
            System.out.println("Request Body: " + requestBody);

            // 发送请求并获取响应
            String responseBody = EntityUtils.toString(httpClient.execute(request).getEntity());

            // 打印 API 响应，便于调试
            System.out.println("API Response: " + responseBody);

            // 解析响应
            JsonNode responseJson = objectMapper.readTree(responseBody);

            // 检查是否有错误
            if (responseJson.has("error")) {
                String errorMessage = responseJson.path("error").path("message").asText();
                return ResponseEntity.status(500).body("API Error: " + errorMessage);
            }

            // 检查 choices 字段是否存在且非空
            JsonNode choicesNode = responseJson.path("choices");
            if (choicesNode.isArray() && choicesNode.size() > 0) {
                JsonNode firstChoice = choicesNode.get(0);
                String answer = firstChoice.path("message").path("content").asText();
                return ResponseEntity.ok(answer);
            } else {
                return ResponseEntity.status(500).body("No valid response from AI service");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error calling AI service: " + e.getMessage());
        }
    }
    @Data
    private static class ModelRequest {
        private String model;
        private List<Message> messages;
        private int max_tokens;
        private double temperature;

        public ModelRequest(String model, String prompt, int max_tokens, double temperature) {
            this.model = model;
            this.messages = new ArrayList<>();
            this.messages.add(new Message("user", prompt));
            this.max_tokens = max_tokens;
            this.temperature = temperature;
        }
    }
    @Data
    private static class Message {
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
}
