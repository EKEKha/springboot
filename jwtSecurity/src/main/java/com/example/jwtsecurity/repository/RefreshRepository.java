package com.example.jwtsecurity.repository;

import com.example.jwtsecurity.entity.RefreshEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RefreshRepository extends JpaRepository<RefreshEntity,Integer> {


    //커스텀 메서드
    Boolean existsByRefresh(String refresh);

    
    @Transactional
    void deleteByRefresh(String refresh);
}
