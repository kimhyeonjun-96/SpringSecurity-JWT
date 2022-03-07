package com.cos.jwt.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwt.config.auth.JwtPrincipalDetails;
import com.cos.jwt.model.JwtUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 있음
// /login 요청해서 username, password를 전송하면 (post)
// UsernamePasswordAuthenticationFilter이 동작을 한다
// 하지만 동작을 하지 않는 이유는 formlogin을 disable 했기에 동작을 하지 않는다
// 동작하도록 하기 위해서는 JwtAuthenticationFilter를 JwtSecurotyConfig에 등록만 하면 된다
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    /*
    *  /login 요청이 들어오면 로그인 시도를 위해서 실행되는 메소드!!!
    *  이후 Db에서 id와 password만 검사를 한다
    *  1. username과 password를 받는다
    *  2, 정상인지 로그인 시도를 한다
    *     제일 간단한 방법이 authenticationManager로 로그인 시도를 하면 PrincipalDetailsService가 호출이 된다
    *     loadUserByUsername 메소드가 실행이 된다
    *  3. 정상적으로 PrincipalDetails가 리턴이 된다면 PrincipalDetails를 세션에 담고
    *     세션에 담아주지 않으면 권한관리가 되지 않는다!!
    *     권한관리를 하지 않을거면 하지 않아도 된다!
    *  4. JWT토큰을 만들어서 응답을 해주기만 하면 된다
    * */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        System.out.println("JwtAuthenticationFilter : 로그인 시도 중");
        // 1. username, password 받기
        // request.getInputStream() 안에 username, password가 담겨있다
        try {
            // www-form-urlencoded 로 보냈을 경우 아래와 같이 진행
//            BufferedReader br = request.getReader();
//
//            String input = null;
//            while ((input = br.readLine()) != null){
//                System.out.println(input);
//            }
            ObjectMapper om = new ObjectMapper();
            JwtUser user = om.readValue(request.getInputStream(), JwtUser.class);

            System.out.println(user);

            // 토큰 생성
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getJwtUsername(), user.getJwtPassword());

            // 로그인 시도
            // 아래의 코드가 실행되면 PricipalDetailsService의  loadUserByUsername() 함수가 실행 됨
            // 인증을 해준다
            // 정상이면 리턴이 된다. authentication에는 로그인 정보가 담긴다
            // DB에 있는 username과 password가 일치한다는 의미
            Authentication authentication = authenticationManager.authenticate(authenticationToken);


            JwtPrincipalDetails principalDetails = (JwtPrincipalDetails) authentication.getPrincipal();
            System.out.println("로그인 성공 및 완료 >> " + principalDetails.getUsername()); // 찍히면 로그인이 정상적으로 실행

            System.out.println("11===========================================================");
            // authentication 객체가 리턴 될 때 session영역에 저장을 해야하고 리턴 한다
            // 리턴의 이유는 권한 관리를 security가 대신 해주기 때문에 편하려고 한다
            // 굳이 JWT토큰을 사용하면서 세션을 만들 이유가 없다. 하지만 권한 처리때문에 session에 넣어준다

            return authentication;

        } catch (IOException e) {
            e.printStackTrace();
        }
//        return super.attemptAuthentication(request, response);
        System.out.println("22===========================================================");
        return null;
    }

    /*
    * attemptAuthentication함수 실행 후 인증이 정상적으로 되었으면 successfulAuthentication함수가 실행이 된다
    * successfulAuthentication에서 JWT토큰을 만들어서 request 요청한 사용자에게 JWT토큰을 응답하면 된다
    * */

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication 실행 : 인증이 완료!!");

        JwtPrincipalDetails principalDetailis = (JwtPrincipalDetails) authResult.getPrincipal();
        // RSA방식은 아니다
        // Hash 암호방식이다 시크릿 값인  cos를 알아야 한다
        String jwtToken = JWT.create()
                .withSubject("cos 토큰") // withSubject는 토큰이름, 크게 의미없다 principalDetailis.getUsername()
                .withExpiresAt(new Date(System.currentTimeMillis() + (60000*10))) // withExpiresAt : 만로시간  JwtProperties.EXPIRATION_TIME
                .withClaim("id", principalDetailis.getUser().getJwtId())
                .withClaim("username", principalDetailis.getUser().getJwtUsername()) // username은 그다지 필요치 않다
                .sign(Algorithm.HMAC512("cos")); // JwtProperties.SECRET

        response.addHeader("Authrization", "Bearer "+jwtToken); // 사용자에게 응답을 한다
    }
}
