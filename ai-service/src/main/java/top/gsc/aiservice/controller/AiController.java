package top.gsc.aiservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AiController {
    private final String API_KEY = "sk-93de23010787470eb28b8d5ef28a50a0";
    private final String MODEL_URL = "https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions"; // 替换为实际的 API URL

    @GetMapping("/ask")
    public ResponseEntity<String> askQuestion(@RequestParam String question) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(MODEL_URL);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Authorization", "Bearer " + API_KEY);

            // 构建请求体
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(new ModelRequest(
                    "qwen-max",
                    question,
                    100,
                    0.7
            ));
            request.setEntity(new StringEntity(requestBody));

            // 发送请求并获取响应
            String responseBody = EntityUtils.toString(httpClient.execute(request).getEntity());

            // 解析响应，提取回答内容
            JsonNode responseJson = objectMapper.readTree(responseBody);
            String answer = responseJson
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

            return ResponseEntity.ok(answer);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error calling AI service");
        }
    }

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

        // Getter 和 Setter 方法
        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public List<Message> getMessages() {
            return messages;
        }

        public void setMessages(List<Message> messages) {
            this.messages = messages;
        }

        public int getMax_tokens() {
            return max_tokens;
        }

        public void setMax_tokens(int max_tokens) {
            this.max_tokens = max_tokens;
        }

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }
    }

    private static class Message {
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        // Getter 和 Setter 方法
        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}