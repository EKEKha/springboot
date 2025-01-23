package com.example.jwtsecurity.config;


import com.example.jwtsecurity.jwt.JWTFilter;
import com.example.jwtsecurity.jwt.JWTUtil;
import com.example.jwtsecurity.jwt.LoginFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //AuthenticationManager가 인자로 받은  AuthenticationConfiguration 객체 생성자 주입
     private final AuthenticationConfiguration authenticationConfiguration;
     private final JWTUtil jwtUtil;

     public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil){
         this.authenticationConfiguration = authenticationConfiguration;
         this.jwtUtil = jwtUtil;
     }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //AuthenticationManager Bean  등록, LoginFilter 에서 생성자 주입 사용하기 위해.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)  throws Exception {

         http
                 .cors((cors) -> cors
                         .configurationSource(new CorsConfigurationSource() {
                             @Override
                             public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                                 CorsConfiguration configuration = new CorsConfiguration();

                                 configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                                 configuration.setAllowedMethods(Collections.singletonList("*"));
                                 configuration.setAllowCredentials(true);
                                 configuration.setAllowedHeaders(Collections.singletonList("*"));
                                 configuration.setMaxAge(3600L);

                                 configuration.setExposedHeaders(Collections.singletonList("Authorization"));


                                 return configuration;

                             }
                         }));

        //csrf disable 세션방식과 관련됐으므로  disable  처리
        http
                .csrf((auth) -> auth.disable());

        // form  로그인 방식  disable ,  API  형태로  필요  X
        http
                .formLogin((auth) -> auth.disable());

        // http basic  인증방식  disable, API  형태로  필요  X
        http
                .httpBasic((auth) -> auth.disable());

        //경로별 인가 작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login","/","join").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated());//나머지는 로그인한 사용자만 접근 가능

        //요청시  jwt 검증 후  권한 접근을 가진 경로일경우 체크
        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);

        //커스텀한 필터 UsernamePasswordAuthenticationFilter 자리에 추가  LoginFilter()는 authenticationManager인자를 받음 authenticationManager는 authenticationConfiguration인자를 받음, 로그인...시 jwt 토큰생성
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class); // 로그인 필터를 UsernamePasswordAuthenticationFilter 자리에 등록하겠다

        //세션 설정 중요!!!  STATELESS
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy((SessionCreationPolicy.STATELESS)));

        return http.build();
    }
}
