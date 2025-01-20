package com.example.testsecurity.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티가 관리할 클래스로 사용하기 위해
public class SecurityConfig {



    /*스프링에서 권장하는 암호화  BCrypt  단방향(해시)*/
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){

        return new BCryptPasswordEncoder();
    }

    /*권한 A, 권한 B, 권한 C가 존재하고 권한의 계층은 “A < B < C”라고 설정을 진행하고 싶은 경우 , 권한이 많을경우  hasAnyRole로 작성하기 어렵기때문*/
    @Bean
    public RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();

        // C > B > A (6.3 이후 버전에서 사용 불가)
        hierarchy.setHierarchy("ROLE_C > ROLE_B\n" +
                "ROLE_B > ROLE_A");

        return hierarchy;


//        http
//                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers("/login").permitAll()
//                        .requestMatchers("/").hasAnyRole("A") 자동으로  A,B,C  접근 가능
//                        .requestMatchers("/manager").hasAnyRole("B")  B,C 접근 가능
//                        .requestMatchers("/admin").hasAnyRole("C")  C 접근가능
//                        .anyRequest().authenticated()
//                );


    }

    /*6.3 이후 버전 */
/*
    @Bean
    public RoleHierarchy roleHierarchy() {

        return RoleHierarchyImpl.fromHierarchy("""
            ROLE_C > ROLE_B
            ROLE_B > ROLE_A
            """);
    }

    @Bean
    public RoleHierarchy roleHierarchy() {

        return RoleHierarchyImpl.withRolePrefix("접두사_") //접두사 생략시 자동으로  ROLE_
                .role("C").implies("B")
                .role("B").implies("A")
                .build();
    }

*/
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        /*****동작은 위에서 부터 실행되므로 주의해서 설정하자*****/
        http
                .authorizeHttpRequests((auth) -> auth
                    .requestMatchers("/","/login","/join" ,"/joinProc").permitAll()  //로그인 없이 모두 접근가능
                    .requestMatchers("/admin").hasRole("ADMIN")  // 로그인 +  ADMIN 이라는  Role 을 가지고 있어야 접근
                    .requestMatchers("/my/**").hasAnyRole("ADMIN","USER")
                    .anyRequest().authenticated() //마지막엔 위에서 처리하고 남은 나머지 요청들에 처리 ( authenticated :  로그인 한 모든 사용자가 접근가능)
        );

        /*****로그인 페이지 및 로그인 요청 url  처리 *****/

        //1.  form login
        http
                .formLogin((auth)   -> auth
                        .loginPage("/login") //로그인 페이지 설정하면 시큐리티가 리다이렉트 시켜줌,
                        .loginProcessingUrl("/loginProc")
                        .permitAll()
                );

        //2. http Basic 방식 :아이디와 비밀번호를 Base64 방식으로 인코딩한 뒤 HTTP 인증 헤더에 부착하여 서버측으로 요청을 보내는 방식
//        http
//                .httpBasic(Customizer.withDefaults());




        /*csrf 설정 */
        //앱에서 사용하는  API 써버의 경우 보통 세션을 STATELESS로 관리하기 때문에 스프링 시큐리티  crsf enable  설정을 진행하지 않아도 된다. + jwt 사용하여도
//        http
//                .csrf((auth) -> auth.disable());


        /*****다중로그인*****/
        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1)            // 하나의 아이디에 대한 다중 로그인 허용 갯수
                        .maxSessionsPreventsLogin(true)); //다중 로그인 갯수 초과 할경우 처리방법  true :  새로운 로그인 차단 ,  false : 기존 세션 하나 삭제


        /****세션 고정 공격 방지*****/
        //none :  세션 정보 변경 안함 , 보호 안됨
        // newSession :  로그인 시 세션 새로 생성 , 기존  ananymos 세션에서 로그인시 새로운 세션으로 생성
        // changeSessionId : 로그인 시 동일한 세션에 대한 id 변경 ,  주로 사용
        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId()
                );



        return http.build();
    }


//    인메모리 방식  db  사용  x
    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user1 = User.builder()
                .username("user1")
                .password(bCryptPasswordEncoder().encode("1234"))
                .roles("ADMIN")
                .build();

        UserDetails user2 = User.builder()
                .username("user2")
                .password(bCryptPasswordEncoder().encode("1234"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
}
