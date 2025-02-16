package com.example.oauthjwt.respository;

import com.example.oauthjwt.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <UserEntity,Long> {

    UserEntity findByUsername(String username);

}
