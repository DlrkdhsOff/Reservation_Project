package com.zero.reservation.model.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ReviewDTO {
    @NotNull(message = "매장 아이디를 입력해주세요")
    private long storeId;

    @NotNull(message = "예약 날짜를 입력해주세요")
    private String reservationDate;

    @NotNull(message = "예약 시간을 입력해주세요")
    private String reservationTime;

    @NotNull(message = "리뷰를 입력해주세요")
    private String review;

}
