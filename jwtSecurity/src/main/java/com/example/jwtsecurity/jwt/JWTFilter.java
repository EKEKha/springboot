package com.example.jwtsecurity.jwt;

import com.example.jwtsecurity.dto.CustomUserDetails;
import com.example.jwtsecurity.entity.UserEntity;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * JWT 통과 검증 커스텀 필터
 *
 * 요청 헤더  Authorization 키에  JWT 가 존재하는 경우  JWT를 검증하고 강제로  SecurityContextHolder에 세션생성 (해당 요청이 끝나면 소멸)
 */
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        //request에서 헤더를 찾음
//        String authorization = request.getHeader("Authorization");
//
//        if(authorization ==null || !authorization.startsWith("Bearer ")){
//
//            System.out.println("token null");
//            filterChain.doFilter(request,response); // 이 필터를 종료하고 받은  request, response 를 다음 필터로 전달
//
//            //조건이 해당되면 메소드 종료 (필수)
//            return;
//        }
//
//
//        String token
//                 = authorization.split(" ")[1];
//
//        //토큰 소멸 시간 검증
//        if(jwtUtil.isExpired(token)){
//            System.out.println("token expired");
//            filterChain.doFilter(request,response); // request, response 를 다음 필터로 전달
//
//            //조건이 해당되면 메소드 종료 (필수)
//            return;
//        }
//
//
//        //토큰에서  username과  Role 획득
//
//        String username = jwtUtil.getUsername(token);
//        String role = jwtUtil.getRole(token);
//
//        //UserEntity를 생성하여 값  set
//        UserEntity userEntity= new UserEntity();
//
//        userEntity.setUsername(username);
//        userEntity.setRole(role);
//        userEntity.setPassword("temppassword"); //비밀번호는 매번  db 에 요청해야하기 때문에 임시비밀번호로 넣어줌
//
//        //UserDetails에 회원 정보 객체 담기
//        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);
//
//        //스프링 시큐리티 인증 토큰
//        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails,null,customUserDetails.getAuthorities());
//
//        //세션에 사용자 등록
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//
//        filterChain.doFilter(request,response);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //request에서 헤더를 찾음
        String accessToken = request.getHeader("access");

        if(accessToken ==null ){

            System.out.println("token null");
            filterChain.doFilter(request,response); // 이 필터를 종료하고 받은  request, response 를 다음 필터로 전달

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }


        //토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        try{
            jwtUtil.isExpired(accessToken);
        }catch (ExpiredJwtException e){

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("access token expired");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;

        }



        //토큰이 access인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(accessToken);

        if(!category.equals("acceess")){
            //response body
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        //username, role   값을 획득하여 일시적인 세션 생성
        String username = jwtUtil.getUsername(accessToken);
        String role = jwtUtil.getRole(accessToken);

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setRole(role);
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails ,null,customUserDetails.getAuthorities());

        filterChain.doFilter(request,response);
    }



}
