package com.fs.client1;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("unauthorizedEntryPoint")
public class UnauthorizedEntryPoint  implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //Authorization:Bearer
        //可以写到8080，由8080告知认证服务器的登录地址，这样应用只需要知道认证后端地址就可以了
        String url = String.format("http://localhost:8083/login?response_type=code&client_id=client1&redirect_uri=http://localhost:8084%s&scope=all", "/home");
        Result result = new Result();
        result.setCode(800);
        result.setMsg("ok");
        result.setData(url);
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }
}
