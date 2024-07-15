package com.zero.reservation.repository;

import com.zero.reservation.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Member, String> {
    boolean existsByUserId(String userId);

    Member findByUserIdAndPassword(String userId, String password);
}
