package com.fs.auth.mobile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SmsCodeAuthenticationProvider implements AuthenticationProvider {
    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;

    public SmsCodeAuthenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken adminLoginToken = (SmsCodeAuthenticationToken) authentication;
//        System.out.println("===进入Admin密码登录验证环节====="+ JSON.toJSONString(adminLoginToken));
        UserDetails userDetails = userDetailsService.loadUserByUsername(adminLoginToken.getName());
        //matches方法，前面为明文，后续为加密后密文
        //匹配密码。进行密码校验
        if (passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        }
        throw new BadCredentialsException("用户名密码不正确");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
