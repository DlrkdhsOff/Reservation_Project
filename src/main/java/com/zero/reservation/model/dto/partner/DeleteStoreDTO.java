package com.zero.reservation.model.dto.partner;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


// 매장 삭제
@Data
public class DeleteStoreDTO {

    @NotNull(message = "매장 번호를 입력해주세요")
    private long storeId;
}
