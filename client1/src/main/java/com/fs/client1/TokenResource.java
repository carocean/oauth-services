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

/**
 * 该类用于客户端web请求令牌，该类作为中介向认证服务器请求，不能删除这些方法
 */
@RestController
public class TokenResource {
    @Autowired
    RestTemplate restTemplate;

    /*
    提供给客户端获取token，对于系统的授权类型，如授权码模式，需要跟根已获得的授权码向/oauth/token获取令牌。
    对于系统的简化模式，通过认证入口已直接向客户端通过重定向地址反回了访问令牌，但不含刷新令牌。但简化模式是不充许向/oauth/token请求令牌的
    对于自定义的授权类型：不能走认证流程，唯有向/oauth/token请求令牌。
     */
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
