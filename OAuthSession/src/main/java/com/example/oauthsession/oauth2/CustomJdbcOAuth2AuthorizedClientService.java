package com.example.oauthsession.oauth2;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

public class CustomJdbcOAuth2AuthorizedClientService extends JdbcOAuth2AuthorizedClientService {
    public CustomJdbcOAuth2AuthorizedClientService(JdbcOperations jdbcOperations, ClientRegistrationRepository clientRegistrationRepository) {
        super(jdbcOperations, clientRegistrationRepository);
    }

    public CustomJdbcOAuth2AuthorizedClientService(JdbcOperations jdbcOperations, ClientRegistrationRepository clientRegistrationRepository, LobHandler lobHandler) {
        super(jdbcOperations, clientRegistrationRepository, lobHandler);
    }


    

}
