package com.zero.reservation.model.dto.partner;

import com.zero.reservation.entity.StoreEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;


// 매장 등록
@Data
public class AddStoreDTO {

    @NotNull(message = "매장 명을 입력해주세요")
    private String storeName;

    @NotNull(message = "매장 주소를 입력해주세요")
    private String storeAddress;

    @NotNull(message = "매장 전화번호를 입력해주세요")
    private String storeNumber;

    @NotNull(message = "상세정보를 입력해주세요")
    private String storeInfo;


    public static StoreEntity of(AddStoreDTO parameter, String userId, String userName) {
        return StoreEntity.builder()
                .partnerId(userId)
                .userName(userName)
                .storeName(parameter.getStoreName())
                .storeAddress(parameter.getStoreAddress())
                .storeNumber(parameter.getStoreNumber())
                .storeInfo(parameter.getStoreInfo())
                .addDt(LocalDateTime.now())
                .build();
    }
}
