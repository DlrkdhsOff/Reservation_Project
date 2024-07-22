package com.zero.reservation.model.dto.partner;

import lombok.Data;

@Data
public class DeleteStoreDTO {   // 매장 삭제

    private long no;
    private String storeName;
}
