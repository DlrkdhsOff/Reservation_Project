package com.zero.reservation.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "partner")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;                   // 파트너 이메일
    private String storeName;               // 매장명
    private String tel;                     // 매장 전화번호
    private String storeAddress;            // 매장 주소
    private String storeInfo;               // 매장 상세 설명
    private LocalDate registrationDate;     // 매장 등록일
}
