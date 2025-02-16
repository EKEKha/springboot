package com.example.oauthsession.config;


import com.example.oauthsession.oauth2.CustomClientRegistrationRepo;
import com.example.oauthsession.oauth2.CustomOAuth2AuthorizedClientService;
import com.example.oauthsession.oauth2.SocialClientRegistration;
import com.example.oauthsession.service.CustomOauth2UserService;
import jakarta.servlet.annotation.WebServlet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOauth2UserService customOauth2UserService;

    private final CustomClientRegistrationRepo customClientRegistrationRepo;

    private final CustomOAuth2AuthorizedClientService customOAuth2AuthorizedClientService;

    private JdbcTemplate jdbcTemplate;

    public SecurityConfig(CustomOauth2UserService customOauth2UserService,CustomClientRegistrationRepo customClientRegistrationRepo,CustomOAuth2AuthorizedClientService customOAuth2AuthorizedClientService,JdbcTemplate jdbcTemplate){
        this.customOauth2UserService=customOauth2UserService;
        this.customClientRegistrationRepo =customClientRegistrationRepo;
        this.customOAuth2AuthorizedClientService =customOAuth2AuthorizedClientService;
        this.jdbcTemplate =jdbcTemplate;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .csrf((csrf) -> csrf.disable());

        http
                .formLogin((login)->login.disable());

        http
                .httpBasic((basic)->basic.disable());

//        http
//                .oauth2Login(Customizer.withDefaults());

        http
                .oauth2Login((oauth2)->oauth2
                        .loginPage("/login") // 커스텀한 로그인페이지 사용
                        .clientRegistrationRepository(customClientRegistrationRepo.clientRegistrationRepository()) //  properties가 아닌 클래스형인 oauth registry  등록
                        .authorizedClientService(customOAuth2AuthorizedClientService.oAuth2AuthorizedClientService(jdbcTemplate, customClientRegistrationRepo.clientRegistrationRepository()))//커스텀한 customOAuth2AuthorizedClientService 등록
                        .userInfoEndpoint(userInfoEndpointConfig ->
                                                    userInfoEndpointConfig.userService(customOauth2UserService))); // 커스텀한 customOauth2UserService 서비스가..등록..

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/oauth2/**", "/login/**").permitAll()
                        .anyRequest().authenticated());

        return  http.build();
    }
}
