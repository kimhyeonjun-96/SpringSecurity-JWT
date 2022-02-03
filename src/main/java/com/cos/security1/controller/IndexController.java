package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // View를 리턴한다
public class IndexController {
    // localhost:8181
    // localhost:8181
    @GetMapping({"","/"})
    public  String index(){
        // 머스테치 (스프링 권장 /src/main/resources/가 기본 폴더)
        // 뷰리졸브 설정 : templates (prefix), .mustache (suffix)
        return "index"; // src/main/resources/templates/index.mustache
    }

    @GetMapping("/user")
    public @ResponseBody String user(){
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "manager";
    }

    // 스프링시큐리티가 해당 주고를 낚아챈다!
    // 설정에서 변경을 한다
    // SercurityConfig 파일을 생성 후 작동하지 않음.
    @GetMapping("/login")
    public @ResponseBody String login(){
        return "login";
    }

    @GetMapping("/join")
    public @ResponseBody String join(){
        return "join";
    }

    @GetMapping("/loginProc")
    public @ResponseBody String loginProc(){
        return "회원가입 완료";
    }
}
