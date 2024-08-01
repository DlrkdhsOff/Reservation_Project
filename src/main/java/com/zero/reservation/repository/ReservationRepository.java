package com.zero.reservation.repository;

import com.zero.reservation.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {


    // 사용자 아이디, 예약 날짜, 시간과 일치하는 데이터가 존재하는지 확인
    boolean existsByCustomerIdAndReservationDateAndReservationTime(String userId, String Date, String time);


    // 예약 상태가 'waiting'인 경우 0, 그 외의 경우 1로 설정하여 우선순위 설정.
    // 이후 예약 날짜 오름차순, 예약 시간 오름차순으로 정렬.
    @Query("SELECT e FROM reservation e WHERE e.partnerId = :partnerId " +
            "ORDER BY CASE WHEN e.reservationStatus = 'waiting' THEN 0 ELSE 1 END, e.reservationDate ASC, e.reservationTime ASC")

    // 파트너 아이디를 기반으로 상태와 날짜 및 시간 순으로 예약 목록 정렬하여 반환.
    List<ReservationEntity> findAllByPartnerIdOrderByStatusAndDateAndTime(@Param("partnerId") String partnerId);

    // 매장 아이디, 사용자 이름, 상태와 일치하는 데이터 반환
    ReservationEntity findByStoreIdAndUserNameAndReservationStatus(long storeId, String userName, String status);

    // 사용자 아이디, 매장 아이디, 예약 날짜, 예약 시간, 예약 상태와 일치하는 데이터 반환
    ReservationEntity findByCustomerIdAndStoreIdAndReservationDateAndReservationTimeAndReservationStatus
    (String userId, long storeId, String date, String time, String status);

    // 파트너 아이디, 사용자 아이디, 매장 아이디, 예약 날짜, 예약 시간, 예약 상태와 일치하는 데이터 반환
    ReservationEntity findByPartnerIdAndCustomerIdAndStoreIdAndReservationDateAndReservationTimeAndReservationStatus
    (String userId, String customerId, long storeId, String date, String time, String status);

    // 사용자 아이디, 매장 아이디, 예약 날짜, 예약 시간과 일치하는 데이터 반환
    ReservationEntity findByCustomerIdAndStoreIdAndReservationDateAndReservationTime
    (String customerId, long storeId, String reservationDate, String reservationTime);

    // 매장 아이디에 해당 하는 모든 데이터 반환
    List<ReservationEntity> findAllByStoreId(long storeId);

    // 사용자 아이디에 해당하는 모든 예약 데이터 반환
    List<ReservationEntity> findAllByCustomerId(String userId);
}
