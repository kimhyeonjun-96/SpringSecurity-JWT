package com.cos.jwt.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// @CrossOrigin // 인증이 필요치 않은  요청만 허용!
@RestController
public class RestApiController {

    @GetMapping("/home")
    public String home(){
        return "<h1>home</h1>";
    }
}
