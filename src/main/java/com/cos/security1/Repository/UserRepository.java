package com.cos.security1.Repository;

import com.cos.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// CRUD 함수를 JpaRepository가 들고 있음.
// @Repository라는 어노테이션이 없어도 IoC가 된다. 왜냐하면 JpaRepository를 상속했기 때문에!

public interface UserRepository extends JpaRepository<User, Integer> {

    // 기본적인 CRUD만 있기에 새로 만들어야 한다. findBy는 규칙 Username은 문법
    // select * from user where username = 1? ?==username
    User findByUsername(String username);


}
