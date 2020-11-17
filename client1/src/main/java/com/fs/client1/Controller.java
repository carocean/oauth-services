package com.fs.client1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: fengchangxin
 * @date: 2020/10/25:22:01
 * @description:
 **/
@RestController
public class Controller {

    @GetMapping("/test")
    public String test(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Cookie cookie = new Cookie("SESSION","fassssssss");
        response.addCookie(cookie);
        response.sendRedirect("http://localhost:8082/client2/test");
        return "fffffff";
    }
}
