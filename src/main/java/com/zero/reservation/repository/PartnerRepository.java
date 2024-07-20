package com.zero.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, String> {

    boolean existsByUserIdAndStoreName(String userId, String storeName);
}
