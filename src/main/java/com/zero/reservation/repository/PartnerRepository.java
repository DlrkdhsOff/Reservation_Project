package com.zero.reservation.repository;

import com.zero.reservation.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<StoreEntity,String> {
}
