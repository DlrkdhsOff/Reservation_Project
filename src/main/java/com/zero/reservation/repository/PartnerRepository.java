package com.zero.reservation.repository;

import com.zero.reservation.model.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, String> {

    boolean existsByEmailAndStoreName(String email, String storeName);
}
