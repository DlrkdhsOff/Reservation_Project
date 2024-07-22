package com.zero.reservation.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "reservation")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ReservationEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private String partnerId;               // 파트너 아이디
    private String customerId;              // 사용자 아이디
    private String userName;                // 사용자 이름
    private String tel;                     // 사용자 전화번호
    private String storeName;               // 매장 명
    private String reservationStatus;       // 예약 상태
    private String review;                  // 리뷰
}
