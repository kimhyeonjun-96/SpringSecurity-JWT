package com.cos.security1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity // 활성화, 스프링시큐리티 필터가 스프링 필터체인에 등록
// 스프링시큐리티 필터는 현재 클래스를 말 한다!
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.csrf().disable();
       http.authorizeRequests()
               .antMatchers("/user/**").authenticated()
               .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN')or hasRole('ROLE_MANAGER')")
               .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
               .anyRequest().permitAll()
               // permitAll 만 하면 권한이 없는 페이지로 요청이 들어갈 때, login 페이지를 띄우고 싶으면 아래와 같이 작성한다.
               .and()
               .formLogin()
               .loginPage("/login");
    }
}
