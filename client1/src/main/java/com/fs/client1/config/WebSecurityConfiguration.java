package com.fs.client1.config;

import com.fs.client1.MyAuthenticationEntryPoint;
import com.fs.client1.MyLogoutSuccessHandler;
import com.fs.client1.UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfiguration extends ResourceServerConfigurerAdapter {
    @Autowired
    UnauthorizedEntryPoint unauthorizedEntryPoint;
    @Autowired
    AccessDeniedHandler accessDeniedHandler;
    @Autowired
    MyAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    MyLogoutSuccessHandler myLogoutSuccessHandler;
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                //.tokenStore(tokenStore)//本地令牌校验
                .stateless(true)//关闭session
                .authenticationEntryPoint(authenticationEntryPoint)//authenticationEntryPoint  认证异常流程处理返回
        ;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .logout().logoutSuccessHandler(myLogoutSuccessHandler).clearAuthentication(true).permitAll()
                .and()
                .authorizeRequests()
                //将自定义的/token和 refresh_token两个地址开放
                .antMatchers("/token","/refresh_token", "/oauth2/**","/logout").permitAll()
                .anyRequest()
//                .access("@rbacService.hasPermission(request,authentication)")//rbacService是自定义的鉴权
                .authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(unauthorizedEntryPoint)
        ;
    }

}
