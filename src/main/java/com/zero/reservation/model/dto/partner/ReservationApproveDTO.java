package com.zero.reservation.model.dto.partner;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationApproveDTO {

    @NotNull(message = "매장 번호를 입력해주세요")
    private long storeId;

    @NotNull(message = "매장 명을 입력해주세요")
    private String storeName;

    @NotNull(message = "사용자 명을 입력해주세요")
    private String userName;

}
