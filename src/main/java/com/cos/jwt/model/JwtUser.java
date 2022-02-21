package com.cos.jwt.model;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@Table(name = "jwt_user")
public class JwtUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long jwt_id;
    private String jwt_username;
    private  String jwt_password;
    private  String jwt_roles;
    // roles USER, ADMIN과 같이 role이 여러개일 경우 아래와 같은 메서드 생성
    public List<String> getRoleList(){
        if(this.jwt_roles.length() > 0){
            return Arrays.asList(this.jwt_roles.split(","));
        }
        return new ArrayList<>();
    }

    /*
    * 내가 만들 서버의 role 하나만 있을 경우
    * private  String jwt_role;
    * 만 있으면 된다!!
    * */

}
