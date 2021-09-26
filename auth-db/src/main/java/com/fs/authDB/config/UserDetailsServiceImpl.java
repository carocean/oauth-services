package com.fs.authDB.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author: fengchangxin
 * @create: 2021-09-26 19:14
 * @description: 通过用户名查询用户登录信息
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO 在这里进行数据库查询获取用户信息
        UserDetails user = User.withUsername("admin").password("123456").authorities("ADMIN", "USER").build();
        return user;
    }
}
