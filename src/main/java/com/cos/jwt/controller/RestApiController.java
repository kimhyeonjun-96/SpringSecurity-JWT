package com.cos.jwt.controller;

import com.cos.jwt.model.JwtUser;
import com.cos.jwt.repository.JwtUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

// @CrossOrigin // 인증이 필요치 않은  요청만 허용!
@RestController
public class RestApiController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private  JwtUserRepository userRepository;

    @GetMapping("/home")
    public String home(){
        return "<h1>home</h1>";
    }

    @PostMapping("token")
    public String token(){
        return "<h1>token</h1>";
    }

    @PostMapping("jwtJoin")
    public String join(@RequestBody JwtUser user){

        System.out.println("jwtUsername >> " + user.getJwtUsername());
        System.out.println("jwtPassword >> " + user.getJwtPassword());

        user.setJwtPassword(bCryptPasswordEncoder.encode(user.getJwtPassword()));
        System.out.println("encoding jwtPassword >> " + user.getJwtPassword());
        user.setJwtRoles("ROLE_USER");

        System.out.println("new join user >> " + user);

        userRepository.save(user);

        return "회원가입 완료";
    }}
