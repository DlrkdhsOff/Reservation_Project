package com.zero.reservation.repository;

import com.zero.reservation.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Member, String> {
    boolean existsByUserId(String userId);

    Member findByUserId(String userId);

}
