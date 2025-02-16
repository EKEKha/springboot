package com.example.oauthsession.dto;

public interface Oauth2Response {

    String getProvider(); // 제공자 이름 :  naver,google...

    String getProviderId(); // 아이디

    String getEmail(); //이메일

    String getName();//이름
}
