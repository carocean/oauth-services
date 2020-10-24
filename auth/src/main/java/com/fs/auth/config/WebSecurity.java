package com.fs.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author: fengchangxin
 * @date: 2020/10/20:22:17
 * @description:
 **/
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("http://localhost:8081/#/home")
                .and().authorizeRequests().anyRequest().authenticated()
                .and().csrf().disable();
    }
}
