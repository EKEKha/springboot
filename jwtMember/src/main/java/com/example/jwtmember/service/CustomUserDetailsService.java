package com.example.jwtmember.service;

import com.example.jwtmember.dto.CustomUserDetails;
import com.example.jwtmember.entity.UserEntity;
import com.example.jwtmember.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userData = userRepository.findByLoginId(username);

        if(userData !=null){
            return new CustomUserDetails(userData);
        }

        return  null;
    }
}
