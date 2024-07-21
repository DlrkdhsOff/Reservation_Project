package com.zero.reservation.model.dto.partner;

import com.zero.reservation.entity.StoreEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateStoreDTO {
    @NotNull(message = "매장 번호를 입력해주세요")
    private String no;

    @NotNull(message = "매장 명을 입력해주세요")
    private String storeName;

    @NotNull(message = "매장 주소를 입력해주세요")
    private String storeAddress;

    @NotNull(message = "매장 전화번호를 입력해주세요")
    private String storeNumber;

    @NotNull(message = "상세정보를 입력해주세요")
    private String storeInfo;


    public static StoreEntity of(StoreEntity store, UpdateStoreDTO parameter) {
        store.setStoreName((parameter.getStoreName()));
        store.setStoreAddress((parameter.getStoreAddress()));
        store.setStoreNumber((parameter.getStoreNumber()));
        store.setStoreInfo((parameter.getStoreInfo()));
        return store;
    }
}
