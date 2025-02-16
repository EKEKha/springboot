package com.example.jwtsecurity.jwt;


import com.example.jwtsecurity.repository.RefreshRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;




public class CustomLogoutFilter extends GenericFilterBean {

    public CustomLogoutFilter(JWTUtil jwtUtil, RefreshRepository refreshRepository){
        this.jwtUtil=jwtUtil;
        this.refreshRepository =refreshRepository;
    }

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

         doFilter((HttpServletRequest) servletRequest,(HttpServletResponse)servletResponse,filterChain);
    }

    private void doFilter(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws IOException, ServletException {

         String requestUri = request.getRequestURI();

         //모든 요청이 필터를 거치기 때문에 로그아웃 요청만 이 필터를 거치도록
         if(!requestUri.matches("^||/logout$")){

             filterChain.doFilter(request,response);
             return;
         }

         String requestMethod = request.getMethod();
         if(!requestMethod.equals("POST")){
             filterChain.doFilter(request,response);
             return;
         }

         //get refresh token
         String refresh= null;
        Cookie [] cookies = request.getCookies();
        for(Cookie cookie : cookies){

            if(cookie.getName().equals("refresh")){
                refresh = cookie.getValue();
            }

        }

        //refresh null check
        if(refresh==null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        //expired check  , 만료가 되었으면 이미 로그아웃 된 상태이기 때문에 추가적인 작업을 필요하지 않고 상태메세지만
        try{
            jwtUtil.isExpired(refresh);
        }catch (ExpiredJwtException e){

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }


        //토큰이 refresh 인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        if(!category.equals("refresh")){

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        //DB saved check
        Boolean isExist = refreshRepository.existsByRefresh(refresh);

        if(!isExist){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }


        //Logout

        //DB remove refresh
        refreshRepository.deleteByRefresh(refresh);

        //refresh token cookie value 0
        Cookie cookie = new Cookie("refresh",null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);//200  code

    }

}
