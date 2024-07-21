package com.zero.reservation.service;

import com.zero.reservation.entity.StoreEntity;
import com.zero.reservation.entity.UserEntity;
import com.zero.reservation.model.dto.partner.StoreDTO;
import com.zero.reservation.model.dto.partner.UpdateStoreDTO;
import com.zero.reservation.model.response.Response;
import com.zero.reservation.repository.PartnerRepository;
import com.zero.reservation.repository.UserRepository;
import com.zero.reservation.status.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class PartnerService {

    private final PartnerRepository partnerRepository;

    private final UserRepository userRepository;

    // 매장 추가
    public Response addStore(StoreDTO parameter, String userId) {
        UserEntity user = new UserEntity();

        if (check(userId) == null) {
            user = userRepository.findByUserId(userId);
        }

        StoreEntity store = partnerRepository.save(StoreDTO.of(parameter, userId, user.getUserName()));

        return new Response(Status.SUCCESS_ADD_STORE);
    }

    // 매장 목록 반환
    public List<StoreEntity> getStoreList(String userId) {

        return partnerRepository.findAllByPartnerId(userId);
    }


    // 매장 수정
    public Response updateStore(UpdateStoreDTO parameter, String userId) {
        UserEntity user = new UserEntity();

        if (check(userId) == null) {
            user = userRepository.findByUserId(userId);
        }

        long no = Long.parseLong(parameter.getNo());
        StoreEntity store = partnerRepository.findByNo(no);


        partnerRepository.save(UpdateStoreDTO.of(store, parameter));

        return new Response(Status.SUCCESS_UPDATE_STORE);
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

        return null; // 검증을 통과하면 null 반환
    }

}
