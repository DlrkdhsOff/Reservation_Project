package com.zero.reservation.model.dto.partner;

import com.zero.reservation.entity.ReservationEntity;
import lombok.Data;

@Data
public class ReservationListDTO {

    private long storeId;                   // 매장 아이디
    private String storeName;               // 매장 명
    private String userName;                // 사용자 이름
    private String tel;                     // 사용자 전화번호
    private String reservationDate;         // 예약 일
    private String reservationTime;         // 예약 일
    private String reservationStatus;       // 예약 상태


    public static ReservationListDTO of(ReservationEntity parameter) {
        ReservationListDTO dto = new ReservationListDTO();
        dto.setStoreId(parameter.getStoreId());
        dto.setStoreName(parameter.getStoreName());
        dto.setUserName(parameter.getUserName());
        dto.setTel(parameter.getTel());
        dto.setReservationDate(parameter.getReservationDate());
        dto.setReservationTime(parameter.getReservationTime());
        dto.setReservationStatus(parameter.getReservationStatus());

        return dto;
    }
}
