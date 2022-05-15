package com.fs.client1;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        //response_type=token 简化模式，等同于加上grant_type=implicit参数
        //response_type=code 授权码模式，等同于加上grant_type=authorization_code参数
//        String url = String.format("http://localhost:8083/login?response_type=token&client_id=client1&redirect_uri=http://localhost:8084%s&scope=all", "/home");
        String url = String.format("http://localhost:8083/login?response_type=code&client_id=client1&redirect_uri=http://localhost:8084%s&scope=all", "/home");
        Result result = new Result();
        result.setCode(805);
        result.setMsg("AccessDenied:"+e.getMessage());
        result.setData(url);
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }
}
