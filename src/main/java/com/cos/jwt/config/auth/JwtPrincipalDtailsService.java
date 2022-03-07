package com.cos.jwt.config.auth;

import com.cos.jwt.model.JwtUser;

import com.cos.jwt.repository.JwtUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
* JwtPrincipalDtailsService가 실행되는 때
*  http://localhost:8181/login -> 그런데 formlogin이 disable 되어 있기에 동작을 하지 않는다!
*  요청이 올 때, 동작을 한다
*  스프링시큐리티의 로그인이 /login이기 때문에
* */

@Service
@RequiredArgsConstructor
public class JwtPrincipalDtailsService implements UserDetailsService {

    private final JwtUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("JwtPrincipalDtailsService의 loadUserByUsername");

        JwtUser userEntity = userRepository.findByJwtUsername(username);
//        System.out.println("getJwt_username >>> " + userEntity.getJwtUsername());

        System.out.println("userEntity :: " + userEntity);

        return new JwtPrincipalDetails(userEntity);
    }
}
