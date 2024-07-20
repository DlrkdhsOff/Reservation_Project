package com.zero.reservation.repository;

import com.zero.reservation.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Member, String> {
    boolean existsByEmail(String email);

    Member findByEmail(String email);
}
