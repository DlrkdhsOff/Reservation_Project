package com.zero.reservation.model.dto.partner;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

// 예약 승인
@Data
public class ReservationApproveDTO {

    @NotNull(message = "매장 아이디를 입력해주세요")
    private long storeId;

    @NotNull(message = "사용자 명을 입력해주세요")
    private String userName;

}
