package com.zero.reservation.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PartnerDTO {

    @NotNull(message = "매장 명을 입력해주세요")
    private String storeName;

    @NotNull(message = "매장 위치를 입력해주세요")
    private String storeAddress;

    @NotNull(message = "매장 상세정보를 입력해주세요")
    private String storeInfo;
}
