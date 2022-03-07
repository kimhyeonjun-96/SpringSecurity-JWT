package com.cos.jwt.config;

import com.cos.jwt.config.filter.MyFilter1;
import com.cos.jwt.config.filter.MyFilter3;
import com.cos.jwt.config.jwt.JwtAuthenticationFilter;
import com.cos.jwt.repository.JwtUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;

    @Autowired
    private JwtUserRepository userRepository;

//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilterBefore(new MyFilter1(), BasicAuthenticationFilter.class);
        http.addFilterBefore(new MyFilter3(), SecurityContextPersistenceFilter.class);
//        http.addFilterAfter(new MyFilter3(), BasicAuthenticationFilter.class);
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // --> 이 코드의 의미는 세션을 사용하지 않겠다는 의미이다!
        .and()
        .addFilter(corsFilter)  // @CrossOrigin(인증X) 시큐리티 필터에 등록 인증(O))
        .formLogin().disable()
        // form 태그로 로그인을 하지 않는다는 것
        .httpBasic().disable()
        .addFilter(new JwtAuthenticationFilter(authenticationManager()))// 꼭 넘어가야하는 파라미터가 있다. AuthenticationManager이다. 그래서 만든다. 어디서? 여기에다.  WebSecurityConfigurerAdapter가 가지고 있다
        // http의 데이터는 암호화가 되지 않는다. 그래서 id,pwd 같은 데이터가 전달 시 노출이 된다.
        // 그런 방식이 httpBasic 방식이다
        // 즉, 기본인증 방식이다!
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
