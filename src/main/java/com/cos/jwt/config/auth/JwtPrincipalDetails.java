package com.cos.jwt.config.auth;

import com.cos.jwt.model.JwtUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class JwtPrincipalDetails implements UserDetails {
    private JwtUser user;

    public JwtPrincipalDetails(JwtUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoleList().forEach(r -> {
            authorities.add(()->r);
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getJwtPassword();
    }

    @Override
    public String getUsername() {
        return user.getJwtUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}