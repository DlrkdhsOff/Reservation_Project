package com.zero.reservation.repository;

import com.zero.reservation.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {

    // 아이디 존재 여부 확인
    boolean existsByUserId(String userId);

    // 아이디와 일치하는 데이터 반환
    UserEntity findByUserId(String userId);
}
