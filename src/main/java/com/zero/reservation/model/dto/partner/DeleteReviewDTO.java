package com.zero.reservation.model.dto.partner;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

// 파트너 매장 리뷰 삭제
@Data
public class DeleteReviewDTO {

    @NotNull(message = "사용자 아이디를 입력해주세요")
    private String userId;

    @NotNull(message = "매장 아이디를 입력해주세요")
    private long storeId;

    @NotNull(message = "매장명을 입력해주세요")
    private String storeName;

    @NotNull(message = "예약 날짜를 입력해주세요")
    private String reservationDate;

    @NotNull(message = "예약 시간을 입력해주세요")
    private String reservationTime;
}
