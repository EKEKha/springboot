package com.example.oauthsession.oauth2;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
public class CustomClientRegistrationRepo {

    private final SocialClientRegistration socialClientRegistration;

    public CustomClientRegistrationRepo(SocialClientRegistration socialClientRegistration){
        this.socialClientRegistration = socialClientRegistration;
    }

    public ClientRegistrationRepository clientRegistrationRepository(){

//         카카오,네이버,,등 간편로그인 몇개없기때문에 인메모리에 저장해도 될것같음
        return new InMemoryClientRegistrationRepository(socialClientRegistration.googleClientRegistration(),socialClientRegistration.naverClientRegistration());
    }
}
