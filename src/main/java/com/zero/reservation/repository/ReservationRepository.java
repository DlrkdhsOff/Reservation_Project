package com.zero.reservation.repository;

import com.zero.reservation.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    boolean existsByCustomerIdAndAndStoreNameAndReservationDateAndReservationTime(String userId, String storeName, String reservationDate, String reservationTime);
}
