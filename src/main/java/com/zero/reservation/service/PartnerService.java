package com.zero.reservation.service;

import com.zero.reservation.entity.ReservationEntity;
import com.zero.reservation.entity.StoreEntity;
import com.zero.reservation.entity.UserEntity;
import com.zero.reservation.model.dto.common.Review;
import com.zero.reservation.model.dto.partner.*;
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

import static com.zero.reservation.model.response.DuplicateMethod.getReview;
import static com.zero.reservation.model.response.DuplicateMethod.isPartnerExist;


@Service
@RequiredArgsConstructor

public class PartnerService {

    private final StoreRepository storeRepository;

    private final UserRepository userRepository;

    private final ReservationRepository reservationRepository;

    // 매장 추가
    @Transactional
    public Response addStore(AddStoreDTO parameter, String userId) {

        // 중복된 데이터가 있을 경우
        if (storeRepository.existsByPartnerIdAndStoreNameAndStoreAddress(userId, parameter.getStoreName(), parameter.getStoreAddress())) {
            return new Response(Status.FAILED_ADD_STORE);
        }

        // 로그인 유무, 파트너 사용자인지 확인
        Response response = isPartnerExist(userId, userRepository);

        // response 값이 존재 할 경우
        if (response != null) {
            return response;
        }

        UserEntity user = userRepository.findByUserId(userId);

        storeRepository.save(AddStoreDTO.of(parameter, userId, user.getUserName()));

        return new Response(Status.SUCCESS_ADD_STORE);
    }

    // 매장 목록 반환
    public Object getStoreList(String userId) {

        Response response = isPartnerExist(userId, userRepository);

        if (response != null) {
            return response;
        }

        List<StoreEntity> storeEntityList = storeRepository.findAllByPartnerId(userId);
        List<PartnerStoreListDTO> result = new ArrayList<>();

        for (StoreEntity store : storeEntityList) {
            List<Review> reviews = getReview(reservationRepository, store);
            result.add(PartnerStoreListDTO.of(store, reviews));
        }

        // 매장 목록이 존재하지 않을 경우
        if (result.isEmpty()) {
            return ResponseEntity.ok(new Response(Status.NOT_FOUND_STORE));
        }

        return result;
    }


    // 매장 수정
    @Transactional
    public Response updateStore(UpdateStoreDTO parameter, String userId) {

        Response response = isPartnerExist(userId, userRepository);

        if (response != null) {
            return response;
        }

        // 입력한 매장 아이디와 일치하는 데이터를 저장
        StoreEntity store = storeRepository.findByStoreId(parameter.getStoreId());

        storeRepository.save(UpdateStoreDTO.of(store, parameter));

        return new Response(Status.SUCCESS_UPDATE_STORE);
    }


    // 매장 삭제
    @Transactional
    public Response deleteStore(DeleteStoreDTO parameter, String userId) {

        Response response = isPartnerExist(userId, userRepository);

        if (response != null) {
            return response;
        }

        // 입력 받은 매개변수 매장 아이디, 파트너 아이디와 일치하는 데이터를 삭제
        storeRepository.deleteByStoreIdAndPartnerId(parameter.getStoreId(), userId);

        return new Response(Status.SUCCESS_DELETE_STORE);
    }


    // 매장 예약 내역 불러오기
    public Object getReservationList(String userId) {
        Response response = isPartnerExist(userId, userRepository);

        if (response != null) {
            return response;
        }

        // 예약 내역에서 예약 상태가 승인 대기중인 예약 먼저 날짜 및 시간 순으로 정렬 후 저장
        List<ReservationEntity> list = reservationRepository.findAllByPartnerIdOrderByStatusAndDateAndTime(userId);

        if (list == null || list.isEmpty()) {
            return new Response(Status.FAILED_GET_RESERVATION_LIST);
        }

        List<ReservationListDTO> result = new ArrayList<>();

        // 필요한 데이터만 따로 담아서 저장
        for (ReservationEntity reservation : list) {
            result.add(ReservationListDTO.of(reservation));
        }

        return result;
    }


    // 매장 예약 승인
    public Response reservationApprove(String status, ReservationApproveDTO parameter, String userId) {

        Response response = isPartnerExist(userId, userRepository);

        if (response != null) {
            return response;
        }


        // 매개변수로 입력 받은 매장 아이디, 예약한 사용자명, 예약 상태가 대기중인 데이터를 저장
        ReservationEntity reservation = reservationRepository
                .findByStoreIdAndUserNameAndReservationStatus(
                        parameter.getStoreId(),
                        parameter.getUserName(),
                        "WAITING"
                );

        // 해당 예약건이 존재하지 않을 경우
        if (reservation == null) {
            return new Response(Status.NOT_FOUND_RESERVATION);
        }

        // 예약을 승인 할 경우
        if ("approve".equals(status)) {
            reservation.setReservationStatus("APPROVE");
            response = new Response(Status.SUCCESS_APPROVE_RESERVATION);
        }
        // 예약을 거절 할 경우
        else if ("refuse".equals(status)) {
            reservation.setReservationStatus("REFUSE");
            response = new Response(Status.SUCCESS_APPROVE_RESERVATION);
        }
        // 다른 데이터를 입력 했을 경우
        else {
            return new Response(Status.PARAMETER_APPROVE_RESERVATION_IS_FAILED);
        }

        reservationRepository.save(reservation);

        return response;
    }


    // 리뷰 삭제
    public Response deleteReview(DeleteReviewDTO parameter, String userId) {

        Response response = isPartnerExist(userId, userRepository);

        if (response != null) {
            return response;
        }

        // 입력 받은 매개변수 파트너 아이디, 예약한 사용자 아이디, 예약 날짜, 예약 시간, 예약상태가 COMPLETE인 데이터의 리뷰 항목을 null로 변경
        ReservationEntity reservation = reservationRepository
                .findByPartnerIdAndCustomerIdAndStoreIdAndReservationDateAndReservationTimeAndReservationStatus(
                        userId,
                        parameter.getUserId(),
                        parameter.getStoreId(),
                        parameter.getReservationDate(),
                        parameter.getReservationTime(),
                        "COMPLETE");

        if (reservation == null) {
            return new Response(Status.NOT_FOUND_RESERVATION);
        }

        reservation.setReview(null);
        reservationRepository.save(reservation);

        return new Response(Status.SUCCESS_DELETE_REVIEW);
    }

}
