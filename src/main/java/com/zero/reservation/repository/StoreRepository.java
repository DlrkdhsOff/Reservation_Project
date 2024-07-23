package com.zero.reservation.repository;

import com.zero.reservation.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity,String> {

    boolean existsByPartnerIdAndStoreNameAndStoreAddress(String partnerId, String storeName, String storeAddress);

    StoreEntity findByStoreId(long storeId);

    List<StoreEntity> findAllByPartnerId(String userId);

    void deleteByStoreIdAndStoreNameAndPartnerId(long storeId, String storeName, String partnerId);

    List<StoreEntity> findAllByStoreNameContaining(String storeName);

    List<StoreEntity> findAllByStoreAddressContaining(String storeAddress);

    StoreEntity findByStoreIdAndStoreName(long storeId, String storeName);
}
