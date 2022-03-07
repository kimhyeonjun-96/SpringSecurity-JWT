package com.cos.jwt.config.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter3 implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        // 토큰 : 코스 -> 토큰을 만들어야 한다
        // 언제? id, pwd가 정상적으로 들어와서 로그인이 완료 되면 토클을 만들어 주고 그걸 응답을 해준다.
        // 요청 할 때마다 header에 Authorizartion에 values값으로 토큰을 가지고 온다
        // 그때 토큰이 넘어오면 이 토큰이 내가 만든 토큰이 맞는지만 검증만 하면 된다 ( RSA , HS256 )
        if(req.getMethod().equals("POST")){
            System.out.println("POST 요청됨");
            String headerAuth = req.getHeader("Authorization");
            System.out.println("headerAuth >>> " + headerAuth);
            System.out.println("필터3");

            if(headerAuth.equals("cos")){
                filterChain.doFilter(servletRequest, servletResponse);
            }else {
                PrintWriter outPrintWriter = res.getWriter();
                outPrintWriter.println("인증안됨");
            }
        }
    }
}
