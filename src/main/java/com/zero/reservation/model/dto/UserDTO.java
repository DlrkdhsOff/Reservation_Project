package com.zero.reservation.model.dto;

import com.zero.reservation.model.entity.Partner;
import lombok.Data;

@Data
public class UserDTO {

    private String storeName;
    private String storeAddress;
    private String storeInfo;

    public UserDTO of(Partner partner) {
        UserDTO userDTO = new UserDTO();

        userDTO.setStoreName(partner.getStoreName());
        userDTO.setStoreAddress(partner.getStoreAddress());
        userDTO.setStoreInfo(partner.getStoreInfo());

        return userDTO;
    }
}
