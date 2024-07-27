package com.zero.reservation.model.dto.partner;

import com.zero.reservation.entity.StoreEntity;
import com.zero.reservation.model.dto.common.Review;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PartnerStoreListDTO {
    private long storeId;
    private String storeName;
    private String storeAddress;
    private String storeNumber;
    private String storeInfo;
    private LocalDateTime addDt;
    List<Review> review;

    public static PartnerStoreListDTO of(StoreEntity store, List<Review> reviews) {
        PartnerStoreListDTO dto = new PartnerStoreListDTO();

        dto.setStoreId(store.getStoreId());
        dto.setStoreName(store.getStoreName());
        dto.setStoreAddress(store.getStoreAddress());
        dto.setStoreNumber(store.getStoreNumber());
        dto.setStoreInfo(store.getStoreInfo());
        dto.setAddDt(store.getAddDt());
        dto.setReview(reviews);

        return dto;
    }
}


