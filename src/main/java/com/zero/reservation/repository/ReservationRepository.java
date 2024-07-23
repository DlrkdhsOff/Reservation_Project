package com.zero.reservation.repository;

import com.zero.reservation.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    boolean existsByCustomerIdAndStoreNameAndReservationDateAndReservationTime(String userId, String storeName, String Date, String time);


    @Query("SELECT e FROM reservation e WHERE e.partnerId = :partnerId " +
            "ORDER BY CASE WHEN e.reservationStatus = 'waiting' THEN 0 ELSE 1 END, e.reservationDate ASC, e.reservationTime ASC")
    List<ReservationEntity> findAllByPartnerIdOrderByStatusAndDateAndTime(@Param("partnerId") String partnerId);
}
