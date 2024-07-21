package com.zero.reservation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "store")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long no;

    private String partnerId;       // 파트너 아이디
    private String userName;        // 파트너 이름
    private String storeName;       // 매장 명
    private String storeAddress;    // 매장 주소
    private String storeNumber;     // 매장 전화번호
    private String storeInfo;       // 매장 상세 정보
    private LocalDateTime addDt;    // 매장 등록일

}
