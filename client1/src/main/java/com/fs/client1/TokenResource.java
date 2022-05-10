package com.fs.client1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TokenResource {
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/token")
    public Map<String, Object> token(@RequestParam() String code) throws IOException {
        String url = String.format("http://localhost:8080/oauth/token");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        String text = String.format("grant_type=authorization_code&code=%s&redirect_uri=http://localhost:8084/home&scope=all&client_id=client1&client_secret=client1_secret", code);
        HttpEntity<HashMap<String, Object>> request = new HttpEntity(text, headers);
        String json = restTemplate.postForObject(url, request, String.class);
        Map<String, Object> map = new ObjectMapper().readValue(json, HashMap.class);
        return map;
    }


    @GetMapping("/refresh_token")
    public Map<String, Object> refreshToken(@RequestParam() String refresh_token) throws IOException {
        String url = String.format("http://localhost:8080/oauth/token");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        String text = String.format("grant_type=refresh_token&refresh_token=%s&client_id=client1&client_secret=client1_secret", refresh_token);
        HttpEntity<HashMap<String, Object>> request = new HttpEntity(text, headers);
        String json = restTemplate.postForObject(url, request, String.class);
        Map<String, Object> map = new ObjectMapper().readValue(json, HashMap.class);
        return map;
    }
}
