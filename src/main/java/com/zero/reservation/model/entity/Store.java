package com.zero.reservation.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "store")
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long no;

    private String partnerId;                   // 파트너 아이디
    private String userName;                    // 사용자 이름
    private String userTel;                     // 사용자 전화번호
    private String storeName;                   // 매장 이름
    private LocalDateTime reservationDate;      // 예약 날짜
    private String review;                      // 리뷰
}