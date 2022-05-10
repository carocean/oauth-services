package com.fs.client1;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * @author: fengchangxin
 * @date: 2020/10/25:22:01
 * @description:
 **/
@RestController
public class Controller {
    /* spring security 安全表达式(用于鉴权的）：
    概述
    https://blog.csdn.net/qq_31960623/article/details/120991051?utm_medium=distribute.pc_aggpage_search_result.none-task-blog-2~aggregatepage~first_rank_ecpm_v1~rank_v31_ecpm-11-120991051-null-null.pc_agg_new_rank&utm_term=hasauthority+haspermission&spm=1000.2123.3001.4430
    https://www.5axxw.com/wiki/content/n3c50i  这个比较全
    https://www.javacoder.cn/?p=926 acl大全
        hasRole, hasAnyRole
        hasAuthority, hasAnyAuthority
        全部允许，全部拒绝
        isAnonymous, isRememberMe, isAuthenticated, isFullyAuthenticated
        主体，身份验证
        hasPermission API
    用法：
    permitAll    永远返回true
    denyAll    永远返回false
    anonymous    当前用户是anonymous时返回true
    rememberMe    当前用户是rememberMe用户时返回true
    authenticated    当前用户不是anonymous时返回true
    fullAuthenticated    当前用户既不是anonymous也不是rememberMe用户时返回true
    hasRole（role）    用户拥有指定的角色权限时返回true
    hasAnyRole（[role1，role2]）    用户拥有任意一个指定的角色权限时返回true
    //参见spring security ACL规范：https://www.jianshu.com/p/b971b4e6ec16
    hasPermission(#articleId, 'isEditor')     此表达式已记录并旨在在表达式系统和 Spring Security 的 ACL 系统之间架起桥梁
    hasPermission(1, 'com.example.domain.Message', 'read')  如果当前用户包含对指定对象的访问权限，则返回 true。
    hasAuthority（authority）    用户拥有指定的权限时返回true
    hasAnyAuthority（[authority1,authority2]）    用户拥有任意一个指定的权限时返回true
    hasIpAddress（'127.0.0.1'）    请求发送的Ip匹配时返回true
    #oauth2.hasScope('admin')
    #contact.name == authentication.name
    注：通过and可将多个表达式连接起来，也支持or
    注解有：@PreAuthorize @PreFilter @PostFilter
    比如支持自定义mySecurityService：@PreAuthorize("@mySecurityService.hasPermission(authentication, #foo)")
         */
    @GetMapping("/test")
    @PreAuthorize("hasAuthority('commons') or hasAuthority('ADMIN')")
//    @PreAuthorize("#oauth2.hasScope('ROLE_USER')")//如果用拿着以scope=all请求的令牌访问该方法则肯定被拒
//    @PreAuthorize("hasPermission('')")
    public Result test() {
        Result result = new Result();
        result.setCode(0);
        result.setData("hello client1");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        Object credentials = authentication.getCredentials();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        System.out.println(String.format("principal:%s; credentials:%s; authorities:%s", principal, credentials, authorities));
        return result;
    }

    @GetMapping("/")
    public void callback(HttpServletResponse response) throws IOException {
        System.out.println("0000-------");
        response.sendRedirect("http://localhost:8084/#/home");
    }
}
