package com.example.jwtmember.service;


import com.example.jwtmember.dto.JoinDTO;
import com.example.jwtmember.entity.UserEntity;
import com.example.jwtmember.exception.BusinessException;
import com.example.jwtmember.jwt.UserStatusCode;
import com.example.jwtmember.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserJoinService {


    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDTO joinDTO){
        boolean isUser = userRepository.existsByLoginId(joinDTO.getLoginId());

        if(isUser){
             throw new BusinessException("이미 존재하는 아이디 입니다.");
        }

//        DTO => Entity
        UserEntity userEntity = UserEntity.builder()
                .loginId(joinDTO.getLoginId())
                .password(bCryptPasswordEncoder.encode(joinDTO.getPassword()))
                .role("ROLE_USER")
                .userStatusCode(UserStatusCode.ACTIVE.getCode())
                .build();

        userRepository.save(userEntity);
    }
}
