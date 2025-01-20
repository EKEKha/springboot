package com.example.testsecurity.dto;

import com.example.testsecurity.entity.UserEntity;
import com.example.testsecurity.service.CustomUserDetailService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


public class CustomUserDetails implements UserDetails {

    private UserEntity userEntity;
    public CustomUserDetails(UserEntity userEntity){
        this.userEntity = userEntity;
    }

    @Override //role 값을 return
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {

                return userEntity.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }


    //dataBase 에 잠김,만료에 관한 기능이 없으므로   return true; 로 임시셋팅
    @Override
    public boolean isAccountNonExpired() { //사용자의 계정이 만료
        //return UserDetails.super.isAccountNonExpired();

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {//사용자의 계정이 잠겨있는지?
        //return UserDetails.super.isAccountNonLocked();

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //return UserDetails.super.isCredentialsNonExpired();
        return true;
    }


    @Override
    public boolean isEnabled() {
       //return UserDetails.super.isEnabled();
        return true;

    }

}
