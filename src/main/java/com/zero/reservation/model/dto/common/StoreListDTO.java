package com.zero.reservation.model.dto.common;

import com.zero.reservation.entity.StoreEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoreListDTO {
    private long no;
    private String storeName;
    private String storeAddress;
    private String storeNumber;
    private String storeInfo;
    private LocalDateTime addDt;

    public static StoreListDTO of(StoreEntity store) {
        StoreListDTO list = new StoreListDTO();

        list.setNo(store.getNo());
        list.setStoreName(store.getStoreName());
        list.setStoreAddress(store.getStoreAddress());
        list.setStoreNumber(store.getStoreNumber());
        list.setStoreInfo(store.getStoreInfo());
        list.setAddDt(store.getAddDt());

        return list;
    }
}
