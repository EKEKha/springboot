package com.example.testsecurity.repository;


import com.example.testsecurity.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    boolean existsByUsername(String username); // 대상객체 ,  @Id 의 데이터타입

    UserEntity findByUsername(String username); // 커스텀 메서드
}
