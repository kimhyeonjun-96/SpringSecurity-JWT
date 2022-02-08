package com.cos.security1.config.auth;

import com.cos.security1.Repository.UserRepository;
import com.cos.security1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
* 시큐리티 설정에서 loginProcessingUrl("/login")으로 설정
*  /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행
*
* username은 로그인 페이지에서 넘겨 받는 데이터 명!
* 바꾸기 위해서는 아래와 같이 진행을 한다
*   SecurityConfig객체
*      .usernameParameter("username") 으로 변경을 해야한다!
* */

@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 시큐리티 session(내부 Authentication(내부 UserDetails))
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("username : " + username);

        User userEntity = userRepository.findByUsername(username);

        if(userEntity != null){
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
