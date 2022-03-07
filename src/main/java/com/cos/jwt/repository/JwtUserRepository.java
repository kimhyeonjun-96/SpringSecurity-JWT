package com.cos.jwt.repository;

import com.cos.jwt.model.JwtUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtUserRepository extends JpaRepository<JwtUser, Long> {

    public JwtUser findByJwtUsername(String username);
}
