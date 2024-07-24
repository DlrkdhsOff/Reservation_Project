package com.zero.reservation.service;

import com.zero.reservation.entity.ReservationEntity;
import com.zero.reservation.entity.StoreEntity;
import com.zero.reservation.entity.UserEntity;
import com.zero.reservation.model.dto.partner.*;
import com.zero.reservation.model.dto.common.StoreListDTO;
import com.zero.reservation.model.response.Response;
import com.zero.reservation.repository.ReservationRepository;
import com.zero.reservation.repository.StoreRepository;
import com.zero.reservation.repository.UserRepository;
import com.zero.reservation.status.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class PartnerService {

    private final StoreRepository storeRepository;

    private final UserRepository userRepository;

    private final ReservationRepository reservationRepository;

    // 매장 추가
    @Transactional
    public Response addStore(AddStoreDTO parameter, String userId) {

        if (storeRepository.existsByPartnerIdAndStoreNameAndStoreAddress(userId, parameter.getStoreName(), parameter.getStoreAddress())) {
            return new Response(Status.FAILED_ADD_STORE);
        }

        UserEntity user = new UserEntity();

        Response response = checkNull(userId);

        if (response != null) {
            return response;
        }
        user = userRepository.findByUserId(userId);

        StoreEntity store = storeRepository.save(AddStoreDTO.of(parameter, userId, user.getUserName()));

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

        Response response = checkNull(userId);

        if (response != null) {
            return response;
        }
        user = userRepository.findByUserId(userId);

        long storeId = Long.parseLong(parameter.getStoreId());
        StoreEntity store = storeRepository.findByStoreId(storeId);


        storeRepository.save(UpdateStoreDTO.of(store, parameter));

        return new Response(Status.SUCCESS_UPDATE_STORE);
    }


    // 매장 삭제
    @Transactional
    public Response deleteStore(DeleteStoreDTO parameter, String userId) {

        Response response = checkNull(userId);

        if (response != null) {
            return response;
        }

        storeRepository.deleteByStoreIdAndStoreNameAndPartnerId(parameter.getStoreId(), parameter.getStoreName(), userId);
        return new Response(Status.SUCCESS_DELETE_STORE);
    }


    public Response checkNull(String userId) {
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

    public Object getReservationList(String userId) {
        UserEntity user = userRepository.findByUserId(userId);
        if (user.getRole().equals("ROLE_USER")) {
            return new Response(Status.NOT_PARTNER_USER);
        }
        List<ReservationEntity> list = reservationRepository.findAllByPartnerIdOrderByStatusAndDateAndTime(userId);

        if (list == null || list.isEmpty()) {
            return new Response(Status.FAILED_GET_RESERVATION_LIST);
        }

        List<ReservationListDTO> result = new ArrayList<>();

        for (ReservationEntity reservation : list) {
            result.add(ReservationListDTO.of(reservation));
        }
        return result;
    }

    public Response reservationApprove(String status, ReservationApproveDTO parameter, String userId) {

        if (userId == null || userId.isEmpty()) {
            return new Response(Status.NOT_LOGGING_IN);
        }

        UserEntity user = userRepository.findByUserId(userId);
        if (user.getRole().equals("ROLE_USER")) {
            return new Response(Status.NOT_PARTNER_USER);
        }

        ReservationEntity reservation = reservationRepository.findByStoreIdAndStoreNameAndUserNameAndReservationStatus(
                parameter.getStoreId(), parameter.getStoreName(), parameter.getUserName(), "WAITING"
        );

        Response response = null;
        if ("approve".equals(status)) {

            reservation.setReservationStatus("APPROVE");

            response = new Response(Status.SUCCESS_APPROVE_RESERVATION);
        } else if ("refuse".equals(status)) {
            reservation.setReservationStatus("REFUSE");

            response = new Response(Status.SUCCESS_APPROVE_RESERVATION);
        } else {
            response = new Response(Status.PARAMETER_APPROVE_RESERVATION_IS_FAILED);
        }
        reservationRepository.save(reservation);
        return response;
    }

}
