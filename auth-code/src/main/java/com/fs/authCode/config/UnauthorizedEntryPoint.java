package com.fs.authCode.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2019-06-10 15:43
 */
@Component("unauthorizedEntryPoint")
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        Map<String, String[]> paramMap = request.getParameterMap();
        StringBuilder param = new StringBuilder();
        paramMap.forEach((k, v) -> {
            param.append("&").append(k).append("=").append(v[0]);
        });
        param.deleteCharAt(0);
        String isRedirectValue = request.getParameter("isRedirect");
        if (!StringUtils.isEmpty(isRedirectValue) && Boolean.valueOf(isRedirectValue)) {
            response.sendRedirect("http://oauth.com/authPage/#/login?"+param.toString());
            return;
        }
        String authUrl = "http://oauth.com/auth/oauth/authorize?"+param.toString()+"&isRedirect=true";
        Result result = new Result();
        result.setCode(800);
        result.setData(authUrl);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        writer.print(mapper.writeValueAsString(result));
        writer.flush();
        writer.close();
    }
}
