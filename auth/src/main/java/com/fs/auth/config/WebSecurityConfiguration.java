package com.fs.auth.config;

import com.fs.auth.base.UcSecurityConfig;
import com.fs.auth.mobile.SmsCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * /oauth/authorize:      验证
 * /oauth/token:          获取token
 * /oauth/confirm_access: 用户授权
 * /oauth/error:          认证失败
 * /oauth/check_token:    资源服务器用来校验token
 * /oauth/token_key:      如果jwt模式则可以用此来从认证服务器获取公钥
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private SuccessAuthentication successAuthentication;
    @Autowired
    private FailureAuthentication failureAuthentication;
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    MyLogoutSuccessHandler myLogoutSuccessHandler;
    @Autowired
    SmsCodeSecurityConfig smsCodeSecurityConfig;
    @Autowired
    UcSecurityConfig ucSecurityConfig;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserDetailsService userDetailsService;
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //这里配置全局用户信息
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**", "/css/**", "/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable().sessionManagement().disable()
                .exceptionHandling()
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/oauth/**", "/logout").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successAuthentication).failureHandler(failureAuthentication)
                .and()
                .logout().logoutSuccessHandler(myLogoutSuccessHandler).clearAuthentication(true).permitAll()
                .and().apply(smsCodeSecurityConfig)
                .and().apply(ucSecurityConfig).and()
        ;
        //这里引入扩展登陆的配置
//        http.apply(emailSecurityConfigurerAdapter)
//                .and().apply(mobileSecurityConfigurerAdapter)
//                .and().apply(socialSecurityConfigurerAdapter);
    }

}
