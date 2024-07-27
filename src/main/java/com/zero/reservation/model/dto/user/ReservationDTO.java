package com.zero.reservation.model.dto.user;

import com.zero.reservation.entity.ReservationEntity;
import com.zero.reservation.entity.StoreEntity;
import com.zero.reservation.entity.UserEntity;
import com.zero.reservation.status.ReservationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationDTO {

    @NotNull(message = "매장 아이디를 입력해주세요")
    private long storeId;

    @NotNull(message = "예약 날짜를 입력해주세요")
    private String reservationDate;

    @NotNull(message = "예약 시간을 입력해주세요")
    private String reservationTime;


    public static ReservationEntity of(ReservationDTO parameter, StoreEntity store, UserEntity user) {
        return ReservationEntity.builder()
                .storeId(store.getStoreId())
                .partnerId(store.getPartnerId())
                .customerId(user.getUserId())
                .userName(user.getUserName())
                .tel(user.getTel())
                .storeName(store.getStoreName())
                .reservationDate(parameter.getReservationDate())
                .reservationTime(parameter.getReservationTime())
                .reservationStatus(String.valueOf(ReservationStatus.WAITING))
                .build();
    }

}
