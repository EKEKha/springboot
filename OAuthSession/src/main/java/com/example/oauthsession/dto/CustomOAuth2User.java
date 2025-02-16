package com.example.oauthsession.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final Oauth2Response oauth2Response;
    private final String role;

    public CustomOAuth2User(Oauth2Response oauth2Response, String role) {
        this.oauth2Response=oauth2Response;
        this.role=role;
    }

    @Override
    public Map<String, Object> getAttributes() { //로그인하면 넘어오는 값들?
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {//권한 정의

         Collection<GrantedAuthority> collection = new ArrayList<>();

         collection.add(new GrantedAuthority() {
             @Override
             public String getAuthority() {
                 return role;
             }
         });

        return collection;
    }

    @Override
    public String getName() { //ㅅㅏ용자의 별명이나 이름
        return oauth2Response.getName();
    }

    public String getUsername(){//회원 아이디로 쓸것 아이디는  oauth2Response 로 넘어오는게 없음...


        return oauth2Response.getProvider()+" "+oauth2Response.getProviderId();
    }
}
