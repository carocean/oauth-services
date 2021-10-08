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
    public Result test() {
        System.out.println("11111");
        Result result = new Result();
        result.setCode(0);
        result.setData("hello client1");
        return result;
    }

    @GetMapping("/")
    public void callback(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://client1.com/client1Page/#/home");
    }
}
