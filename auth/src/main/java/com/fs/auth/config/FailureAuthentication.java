package com.fs.auth.config;

import com.fs.auth.common.R;
import com.fs.auth.common.ResultCode;
import com.fs.auth.common.ResultCodeTranslator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: fcx
 * @create: 2018-12-02 09:27
 * @description:
 */
@Component("failureAuthentication")
public class FailureAuthentication extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ResultCode rc = ResultCodeTranslator.translateException(exception);
        Object obj = R.of(rc, exception.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(obj));
    }
}
