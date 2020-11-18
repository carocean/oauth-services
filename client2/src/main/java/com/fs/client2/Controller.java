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
    public Result test() throws IOException {
        System.out.println("222222");
        Result result = new Result();
        result.setCode(0);
        result.setMsg("hello client2");
        return result;
    }
}
