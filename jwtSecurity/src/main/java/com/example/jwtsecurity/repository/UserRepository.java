package com.example.jwtsecurity.repository;


import com.example.jwtsecurity.dto.JoinDTO;
import com.example.jwtsecurity.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    Boolean existsByUsername(String username);


    // Boolean existsByUserName(String username);  ===>  에러.,.,

    UserEntity findByUsername(String username);

}
