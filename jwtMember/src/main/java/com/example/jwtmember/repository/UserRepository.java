package com.example.jwtmember.repository;


import com.example.jwtmember.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    Boolean existsByLoginId(String loginId);


    // Boolean existsByUserName(String username);  ===>  에러.,.,

    UserEntity findByLoginId(String loginId);

}
