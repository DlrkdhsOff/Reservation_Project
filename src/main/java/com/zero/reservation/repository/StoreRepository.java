package com.zero.reservation.repository;

import com.zero.reservation.entity.StoreEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity,String> {

    // 파트너 아이디, 매장명, 매장 주소와 일치하는 데이터가 존재하는지 확인
    boolean existsByPartnerIdAndStoreNameAndStoreAddress(String partnerId, String storeName, String storeAddress);

    // 매장 아이디에 따른 데이터 반환
    StoreEntity findByStoreId(long storeId);

    // 해당 파트너가 등록한 모든 매장 반환
    List<StoreEntity> findAllByPartnerId(String userId);

    // 입력한 파트너 아이디와 매장 아이디와 일치하는 데이터 삭제
    void deleteByStoreIdAndPartnerId(long storeId, String userId);

    // 매개변수로 입력받은 문자열이 들어간 매장 반환
    List<StoreEntity> findAllByStoreNameContaining(String storeName);

    // 매개변수로 입력받은 문자열이 들어간 매장 반환
    List<StoreEntity> findAllByStoreAddressContaining(String storeAddress);


    List<StoreEntity> findAllByOrderByStoreNameAsc();

}
