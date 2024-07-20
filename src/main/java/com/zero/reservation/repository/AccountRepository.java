package com.zero.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Member, String> {
    boolean existsByUserId(String userId);

    Member findByUserIdAndPassword(String userId, String password);
}
