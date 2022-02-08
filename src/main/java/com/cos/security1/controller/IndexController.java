package com.cos.security1.controller;

import com.cos.security1.Repository.UserRepository;
import com.cos.security1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // View를 리턴한다
public class IndexController {
    // localhost:8181
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user){
        System.out.println(user);
        user.setRole("ROLE_USER");
//        userRepository.save(user); // 회원가입이 잘 된다.
        /*
        * 하지만 위와 같이 진행을 하면 비밀번호가 1234로 들어가게 된다
        * 그러면 시큐리티로 로그인을 할 수 없다!
        * 이유는 패스워드가 암호화가 되지 않았기 때문!
        * */
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user);

        // redirect를 붙이면 /loginForm 함수를 호출한다!
        return "redirect:/loginForm";
    }
}
