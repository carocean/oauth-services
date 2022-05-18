package com.fs.auth.config;

import com.fs.auth.common.R;
import com.fs.auth.common.ResultCode;
import com.fs.auth.common.ResultCodeTranslator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
public class MySecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenStore tokenStore(JedisConnectionFactory jedisConnectionFactory) {
        return new RedisTokenStore(jedisConnectionFactory);
    }

    @Bean
    public AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return ((request, response, e) -> {
            response.setStatus(HttpStatus.OK.value());
            response.setHeader("Content-Type", "application/json;charset=utf-8");
            ResultCode rc = ResultCodeTranslator.translateException(e);
            Object obj = R.of(rc, e.getMessage());
            response.getWriter().write(new ObjectMapper().writeValueAsString(obj));
        });
    }

    @Bean
    public WebResponseExceptionTranslator customExceptionTranslator() {
        return (e -> {
            OAuth2Exception exception = (OAuth2Exception) e;
            String errorCode = exception.getOAuth2ErrorCode();
            ResultCode rc = ResultCodeTranslator.translateResultCode(errorCode);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("Content-Type", "application/json;charset=utf-8")
                    .body(R.of(rc, e.getMessage()));
        });
    }
}
