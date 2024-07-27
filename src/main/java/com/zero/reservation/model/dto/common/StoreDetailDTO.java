package com.zero.reservation.model.dto.common;

import com.zero.reservation.entity.StoreEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class StoreDetailDTO {

    private String storeName;
    private String storeAddress;
    private String storeNumber;
    private String storeInfo;
    private LocalDateTime addDt;
    List<Review> review;

    public static StoreDetailDTO of(StoreEntity store, List<Review> reviewList) {
        StoreDetailDTO dto = new StoreDetailDTO();
        dto.setStoreName(store.getStoreName());
        dto.setStoreAddress(store.getStoreAddress());
        dto.setStoreNumber(store.getStoreNumber());
        dto.setStoreInfo(store.getStoreInfo());
        dto.setAddDt(store.getAddDt());
        dto.setReview(reviewList);

        return dto;
    }
}
