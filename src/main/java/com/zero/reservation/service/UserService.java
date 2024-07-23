package com.zero.reservation.service;

import com.zero.reservation.entity.ReservationEntity;
import com.zero.reservation.entity.StoreEntity;
import com.zero.reservation.entity.UserEntity;
import com.zero.reservation.model.dto.common.StoreListDTO;
import com.zero.reservation.model.dto.user.ReservationDTO;
import com.zero.reservation.model.dto.user.UserStoreListDTO;
import com.zero.reservation.model.response.Response;
import com.zero.reservation.repository.ReservationRepository;
import com.zero.reservation.repository.StoreRepository;
import com.zero.reservation.repository.UserRepository;
import com.zero.reservation.status.ReservationStatus;
import com.zero.reservation.status.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final StoreRepository storeRepository;

    private final ReservationRepository reservationRepository;


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

    public Response reservationStore(ReservationDTO parameter, String userId) {
        StoreEntity store = storeRepository.findByStoreIdAndStoreName(parameter.getStoreId(), parameter.getStoreName());
        UserEntity user = userRepository.findByUserId(userId);

        if (store == null) {
            return new Response(Status.NOT_FOUND_STORE);
        }

        if (user == null) {
            return new Response(Status.NOT_FOUND_USER);
        }

        reservationRepository.save(ReservationEntity.builder()
                .storeId(store.getStoreId())
                .partnerId(store.getPartnerId())
                .customerId(user.getUserId())
                .userName(user.getUserName())
                .tel(user.getTel())
                .storeName(store.getStoreName())
                .reservationStatus(String.valueOf(ReservationStatus.WAITING))
                .build());

        return new Response(Status.SUCCESS_RESERVATION_STORE);
    }

}
