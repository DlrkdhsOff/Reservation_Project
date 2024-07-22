package com.zero.reservation.service;

import com.zero.reservation.entity.StoreEntity;
import com.zero.reservation.entity.UserEntity;
import com.zero.reservation.model.dto.partner.DeleteStoreDTO;
import com.zero.reservation.model.dto.partner.StoreDTO;
import com.zero.reservation.model.dto.partner.StoreListDTO;
import com.zero.reservation.model.dto.partner.UpdateStoreDTO;
import com.zero.reservation.model.response.Response;
import com.zero.reservation.repository.StoreRepository;
import com.zero.reservation.repository.UserRepository;
import com.zero.reservation.status.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class PartnerService {

    private final StoreRepository storeRepository;

    private final UserRepository userRepository;

    // 매장 추가
    @Transactional
    public Response addStore(StoreDTO parameter, String userId) {
        UserEntity user = new UserEntity();

        Response response = check(userId);

        if (response != null) {
            return response;
        }
        user = userRepository.findByUserId(userId);

        StoreEntity store = storeRepository.save(StoreDTO.of(parameter, userId, user.getUserName()));

        return new Response(Status.SUCCESS_ADD_STORE);
    }

    // 매장 목록 반환
    public List<StoreListDTO> getStoreList(String userId) {
        List<StoreListDTO> result = new ArrayList<>();
        List<StoreEntity> storeEntityList = storeRepository.findAllByPartnerId(userId);

        for (StoreEntity store : storeEntityList) {
            result.add(StoreListDTO.of(store));
        }

        return result;
    }


    // 매장 수정
    @Transactional
    public Response updateStore(UpdateStoreDTO parameter, String userId) {
        UserEntity user = new UserEntity();
        System.out.println(userId);

        Response response = check(userId);

        if (response != null) {
            return response;
        }
        user = userRepository.findByUserId(userId);

        long no = Long.parseLong(parameter.getNo());
        StoreEntity store = storeRepository.findByNo(no);


        storeRepository.save(UpdateStoreDTO.of(store, parameter));

        return new Response(Status.SUCCESS_UPDATE_STORE);
    }


    // 매장 삭제
    @Transactional
    public Response deleteStore(DeleteStoreDTO parameter, String userId) {

        Response response = check(userId);

        if (response != null) {
            return response;
        }

        storeRepository.deleteByNoAndStoreNameAndPartnerId(parameter.getNo(), parameter.getStoreName(), userId);
        return new Response(Status.SUCCESS_DELETE_STORE);
    }


    public Response check(String userId) {
        if (userId == null || userId.isEmpty()) {
            return new Response(Status.NOT_LOGGING_IN);
        }

        UserEntity user = userRepository.findByUserId(userId);

        if (user == null) {
            return new Response(Status.NOT_FOUND_USER);
        }

        if (user.getRole().equals("ROLE_USER")) {
            return new Response(Status.NOT_PARTNER_USER);
        }

        return null;
    }
}
