package com.example.jwtsecurity.jwt;

import com.example.jwtsecurity.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;


public class LoginFilter  extends UsernamePasswordAuthenticationFilter {  //formLogin  비활성화 했으므로  UsernamePasswordAuthenticationFilter  커스텀 해야함

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }





    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)throws AuthenticationException {


        //클라이언트 요청에서  username,password 추출
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        System.out.println(username);
        System.out.println(password);

        //스프링 시큐리티에서  username과  password를 검증하기 위해서는 token 에 담 아야함
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,password,null);

        // 인증과정  Authentication Manager에게  token 에 담아 전송
        return authenticationManager.authenticate(authToken);

    }

    //로그인 성공 (따로  AuthenticationSuccessHandler 구현해도 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication){
/*

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String username = customUserDetails.getUsername();

        //권한
         Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
         Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
         GrantedAuthority auth = iterator.next();
         String role = auth.getAuthority();

         String token = JWTUtil.createJwt(username,role,60*60*10L);

         // Http  인증 방식은  RFC 7235  정의에 따라 인증 헤더 형태를 가져야 한다.
         // Authorization :  타입 인증 토큰 ,   Bearer 뒤에 띄어쓰기 꼭
         response.addHeader("Authorization" ,"Bearer " + token);

*/

        // 유저정보
        String username = authentication.getName();

        //권한
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        //토큰 2가지 생성 (  access , refresh )
        String access = jwtUtil.createJwt("access",username,role,600000L);
        String refresh = jwtUtil.createJwt("refresh",username,role,86400000L);

        //응답 생성
        response.setHeader("access",access);
        response.addCookie(createCookie("refresh",refresh));
        response.setStatus(HttpStatus.OK.value());
    }

    //로그인 실패
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        response.setStatus(401);
    }


    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key,value);
        cookie.setMaxAge(24*60*60);
        cookie.setHttpOnly(true); //클라이언트단에서 자바스크립트로 해당 쿠키 접근하지 못하도록 설정 필수

        return cookie;

    }

}
