package com.cos.jwt.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // --> 이 코드의 의미는 세션을 사용하지 않겠다는 의미이다!
        .and()
        .addFilter(corsFilter)  // @CrossOrigin(인증X) 시큐리티 필터에 등록 인증(O))
        .formLogin().disable()
        .httpBasic().disable()
        .authorizeRequests()
        .antMatchers("/api/v1/user/**")
        .access("hasRole('ROLE_USER') or hasRole('ROlE_MANAGER') or hasRole('ROlE_ADMIN')")
        .antMatchers("/api/v1/manager/**")
        .access("hasRole('ROlE_MANAGER') or hasRole('ROlE_ADMIN')")
        .antMatchers("/api/v1/admin/**")
        .access("hasRole('ROlE_ADMIN')")
        .anyRequest().permitAll();
    }
}
