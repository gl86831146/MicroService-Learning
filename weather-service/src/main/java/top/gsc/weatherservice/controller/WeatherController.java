package top.gsc.weatherservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WeatherController {
    private static final String API_URL = "http://apis.juhe.cn/simpleWeather/query";
    private static final String API_KEY = "81113533ce661188b1b327cfd9d6a164";

    @GetMapping("/weather")
    public ResponseEntity<String> getWeather(@RequestParam String city) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s?city=%s&key=%s", API_URL, city, API_KEY);

        try {
            String response = restTemplate.getForObject(url, String.class);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve weather data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
