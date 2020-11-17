package com.fs.client2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("222222");
        return "22222";
    }
}
