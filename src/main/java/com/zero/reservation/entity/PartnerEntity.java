package com.zero.reservation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "partner")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PartnerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long no;

    private String userId;          // 파트너 아이디
    private String userName;        // 파트너 이름
    private String storeName;       // 매장 명
    private String storeAddress;    // 매장 주소
    private String storeNumber;     // 매장 전화번호
    private String storeInfo;       // 매장 상세 정보

}
