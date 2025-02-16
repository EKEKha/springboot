package com.example.oauthsession.oauth2;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

//jdbc 저장
//Spring Security는 기본적으로 OAuth2AuthorizedClientService를 사용하여 인증된 OAuth 2.0 클라이언트 정보를 메모리에 저장하기때문에 커스텀하여 db에 저장
@Configuration
public class CustomOAuth2AuthorizedClientService {

    @Bean
    public OAuth2AuthorizedClientService oAuth2AuthorizedClientService(JdbcTemplate jdbcTemplate, ClientRegistrationRepository clientRegistrationRepository) {

        return new JdbcOAuth2AuthorizedClientService(jdbcTemplate ,clientRegistrationRepository); //JdbcOAuth2AuthorizedClientService : 안에  Db  insert 처리하는 과정 처리 되어있음.

        //return new CustomJdbcOAuth2AuthorizedClientService(jdbcTemplate,clientRegistrationRepository);
    }
}
