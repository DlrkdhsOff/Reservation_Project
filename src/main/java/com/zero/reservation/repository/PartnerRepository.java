package com.zero.reservation.repository;

import com.zero.reservation.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerRepository extends JpaRepository<StoreEntity,String> {
    StoreEntity findByNo(long no);

    List<StoreEntity> findAllByPartnerId(String userId);

    void deleteByNoAndStoreNameAndPartnerId(long no, String storeName, String partnerId);
}
