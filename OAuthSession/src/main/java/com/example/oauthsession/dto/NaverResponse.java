package com.example.oauthsession.dto;

import java.util.Map;

public class NaverResponse implements Oauth2Response{

    private final Map<String,Object> attribute;

    public NaverResponse(Map<String,Object> attribute){
        this.attribute = (Map<String, Object>) attribute.get("response");   // 네이버는  json 데이터 내에  response 내에 있음
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();

    }

    @Override
    public String getName() {
        return attribute.get("name").toString();

    }
}
