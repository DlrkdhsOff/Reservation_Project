package com.zero.reservation.model.dto;

import lombok.Data;

@Data
public class StoreDetail {

    private String storeName;
    private String storeAddress;
    private String storeInfo;

    public StoreDetail of(Partner partner) {
        StoreDetail detail = new StoreDetail();

        detail.setStoreName(partner.getStoreName());
        detail.setStoreAddress(partner.getStoreAddress());
        detail.setStoreInfo(partner.getStoreInfo());

        return detail;
    }
}
