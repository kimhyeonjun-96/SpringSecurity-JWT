package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/*
* 만드는 이융
*   시큐리티가  /login 주소 요청이 오면 낚아 채서 로그인을 진행.
*   로그인을 진행이 완료가 되면 시큐리티 session을 만들어 준다  (키값 : Security ContextHolder에 세션 정보를 저장한다).
*   session 공간을 같지만 시큐리티만의 session 공간을 가진다.
*   세션의 정보에는 오브젝트 타입 => Authentication 타입의 객체가 저장이 된다
*   Authentication 안에는 User정보가 있어야 한다.
*   User오브젝트의 타입은 UserDetails 타입 객체
*
*   시큐리티 Session{세션 정보 저장} =>  Authentication객체 => UserDetails객체 == PrincipalDetails
* */
public class PrincipalDetails implements UserDetails {

    private User user; // 콤포지션
    public PrincipalDetails(User user){
        this.user = user;
    }


    // 해당 User의 권한을 리턴하는 곳!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정 만료 여부 확인
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠김 여부 확인
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 기간이 지났는지 확인
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화 되었는지 확인
    @Override
    public boolean isEnabled() {
        return true;
    }

    /*
    *   false가 되는 때
    *       우리 사이트에서 1년동안 회원이 로그인을 안하면 휴먼 계정으로 하기로 함!
    *       현재시간 - 로그인 시간 => 1년을 초과하면 return으로 false로 설정!
    * */
}
