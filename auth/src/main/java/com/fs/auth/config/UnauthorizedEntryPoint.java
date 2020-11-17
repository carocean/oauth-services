package com.fs.auth.config;

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
        Map<String, String[]> param = request.getParameterMap();
        StringBuffer sb = new StringBuffer();
        param.forEach((k, v) -> {
            sb.append("&").append(k).append("=").append(v[0]);
        });
        sb.deleteCharAt(0);
        String isRedirectValue = request.getParameter("isRedirect");
        if (!StringUtils.isEmpty(isRedirectValue) && Boolean.valueOf(isRedirectValue)) {
            response.sendRedirect("http://fcx.com/pc/login?"+sb.toString());
            return;
        }
        StringBuffer url = request.getRequestURL();
        url.append("?").append(sb);
        url.append("&isRedirect=true");
        Result result = new Result();
        result.setCode(800);
        result.setData(url.toString());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        writer.print(mapper.writeValueAsString(result));
        writer.flush();
        writer.close();
    }
}
