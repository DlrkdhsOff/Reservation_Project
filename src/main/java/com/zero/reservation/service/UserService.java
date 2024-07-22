package com.zero.reservation.service;

import com.zero.reservation.entity.StoreEntity;
import com.zero.reservation.model.dto.common.StoreListDTO;
import com.zero.reservation.model.dto.user.UserStoreListDTO;
import com.zero.reservation.repository.StoreRepository;
import com.zero.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final StoreRepository storeRepository;


    // 매장 검색
    public List<StoreListDTO> getUserStoreList(UserStoreListDTO parameter, String userId) {
        List<StoreListDTO> result = new ArrayList<>();
        List<StoreEntity> storeEntityList = new ArrayList<>();

        // 매장명 검색
        if (!(parameter.getStoreName() == null || parameter.getStoreName().isEmpty())) {
            storeEntityList = storeRepository.findAllByStoreNameContaining(parameter.getStoreName());
            System.out.println(1);

        // 매장 주소 검색
        } else if (!(parameter.getStoreAddress() == null || parameter.getStoreAddress().isEmpty())) {
            storeEntityList = storeRepository.findAllByStoreAddressContaining(parameter.getStoreAddress());
            System.out.println(2);

        // 일반 검색
        } else {
            storeEntityList = storeRepository.findAll();
            System.out.println(3);
        }

        for (StoreEntity store : storeEntityList) {
            result.add(StoreListDTO.of(store));
        }

        return result;
    }

}
