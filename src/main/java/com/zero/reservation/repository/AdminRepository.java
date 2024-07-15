package com.zero.reservation.repository;

import com.zero.reservation.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

    boolean existsByUserIdAndStoreName(String userId, String storeName);
}
