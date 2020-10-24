package com.fs.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: fengchangxin
 * @date: 2020/10/20:21:23
 * @description:
 **/
@RestController
public class Controller {

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
