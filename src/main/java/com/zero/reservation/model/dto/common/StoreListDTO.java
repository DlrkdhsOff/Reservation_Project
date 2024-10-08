package com.zero.reservation.model.dto.common;

import com.zero.reservation.entity.StoreEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoreListDTO {

    private long storeId;
    private String storeName;
    private String storeAddress;
    private LocalDateTime addDt;

    public static StoreListDTO of(StoreEntity store) {
        StoreListDTO dto = new StoreListDTO();
        dto.setStoreId(store.getStoreId());
        dto.setStoreName(store.getStoreName());
        dto.setStoreAddress(store.getStoreAddress());
        dto.setAddDt(store.getAddDt());

        return dto;
    }
}
