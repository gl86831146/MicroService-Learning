package top.gsc.requestservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class ConsumerController3 {
    private final String BASE_URL = "https://www.wanandroid.com/article/list/";

    private final WebClient webClient = WebClient.builder()
            .baseUrl(BASE_URL)
            .build();

    @GetMapping("/webClientTest")
    public Mono<String> webClientTest(int page) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("{page}/json")
                        .build(page))
                .retrieve()
                .bodyToMono(String.class);
    }
}
