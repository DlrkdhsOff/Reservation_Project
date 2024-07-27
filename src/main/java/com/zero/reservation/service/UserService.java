package com.zero.reservation.service;

import com.zero.reservation.entity.ReservationEntity;
import com.zero.reservation.entity.StoreEntity;
import com.zero.reservation.entity.UserEntity;
import com.zero.reservation.model.dto.user.DeleteReviewDTO;
import com.zero.reservation.model.dto.common.StoreListDTO;
import com.zero.reservation.model.dto.user.KioskDTO;
import com.zero.reservation.model.dto.user.ReservationDTO;
import com.zero.reservation.model.dto.user.ReviewDTO;
import com.zero.reservation.model.dto.user.UserStoreListDTO;
import com.zero.reservation.model.response.Response;
import com.zero.reservation.repository.ReservationRepository;
import com.zero.reservation.repository.StoreRepository;
import com.zero.reservation.repository.UserRepository;
import com.zero.reservation.status.ReservationStatus;
import com.zero.reservation.status.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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


    // 매장 예약
    public Response reservationStore(ReservationDTO parameter, String userId) {

        StoreEntity store = storeRepository.findByStoreIdAndStoreName(parameter.getStoreId(), parameter.getStoreName());
        UserEntity user = userRepository.findByUserId(userId);

        if (store == null) {
            return new Response(Status.NOT_FOUND_STORE);
        }

        if (user == null) {
            return new Response(Status.NOT_FOUND_USER);
        }

        if (reservationRepository.existsByCustomerIdAndStoreNameAndReservationDateAndReservationTime(
                userId, parameter.getStoreName(), parameter.getReservationDate(), parameter.getReservationTime()
        )) {

            return new Response(Status.FAILED_RESERVATION_STORE);
        }

        Response result = checkDateTime(parameter.getReservationDate(), parameter.getReservationTime());
        if (result != null) {
            return result;
        }

        reservationRepository.save(ReservationDTO.of(parameter, store, user));


        return new Response(Status.SUCCESS_RESERVATION_STORE);
    }


    // 지난 날짜인지 확인
    private Response checkDateTime(String date, String time) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            LocalDate reservationDate = LocalDate.parse(date, dateFormatter);
            LocalTime reservationTime = LocalTime.parse(time, timeFormatter);

            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now();

            if (reservationDate.isBefore(today)) {
                return new Response(Status.FAILED_BEFORE_DATE);
            } else if (reservationDate.isEqual(today) && reservationTime.isBefore(now)) {
                return new Response(Status.FAILED_BEFORE_TIME);

            }
        } catch (DateTimeParseException e) {
            return new Response(Status.FAILED_DATE_FORMATTER);
        }
        return null;
    }


    public Response checkReservation(KioskDTO parameter, String userId) {

        if (userId == null || userId.isEmpty()) {
            return new Response(Status.NOT_LOGGING_IN);
        }

        ReservationEntity reservation = reservationRepository
                .findByCustomerIdAndStoreNameAndReservationDateAndReservationTime(userId, parameter.getStoreName(),
                        parameter.getReservationDate(), parameter.getReservationTime());


        switch (reservation.getReservationStatus()) {
            case "CANCEL" -> {
                return new Response(Status.RESERVATION_STATUS_CANCEL);
            }
            case "WAITING" -> {
                return new Response(Status.RESERVATION_STATUS_WAITING);
            }
            case "REFUSE" -> {
                return new Response(Status.RESERVATION_STATUS_REFUSE);
            }
            case "COMPLETE" -> {
                return new Response(Status.RESERVATION_STATUS_COMPLETE);
            }
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            LocalDate reservationDate = LocalDate.parse(reservation.getReservationDate(), dateFormatter);
            LocalTime tenMinutesBeforeReservation = LocalTime.parse(reservation.getReservationTime(), timeFormatter)
                    .minusMinutes(10);

            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now();

            Response response = null;
            if (today.isBefore(reservationDate)) {
                response = new Response(Status.FAILED_KIOSK_BEFORE_DATE);
            } else if (today.isAfter(reservationDate)) {
                reservation.setReservationStatus(String.valueOf(ReservationStatus.CANCEL));
                response = new Response(Status.FAILED_KIOSK_AFTER_DATE);
            } else if (reservationDate.isEqual(today) && now.isAfter(tenMinutesBeforeReservation)) {
                reservation.setReservationStatus(String.valueOf(ReservationStatus.CANCEL));
                response = new Response(Status.FAILED_KIOSK_AFTER_TIME);
            } else {
                reservation.setReservationStatus(String.valueOf(ReservationStatus.COMPLETE));
                response = new Response(Status.SUCCESS_STORE_ENTRANCE);
            }

            reservationRepository.save(reservation);
            return response;
        } catch (DateTimeParseException e) {
            return new Response(Status.FAILED_DATE_FORMATTER);
        }
    }

    public Response review(ReviewDTO parameter, String userId) {

        if (userId == null || userId.isEmpty()) {
            return new Response(Status.NOT_LOGGING_IN);
        }

        UserEntity user = userRepository.findByUserId(userId);
        if (user.getRole().equals("ROLE_PARTNER")) {
            return new Response(Status.IS_PARTNER_USER);
        }

        ReservationEntity reservation = reservationRepository
                .findByCustomerIdAndStoreIdAndStoreNameAndReservationDateAndReservationTimeAndReservationStatus(
                        userId, parameter.getStoreId(),
                        parameter.getStoreName(),
                        parameter.getReservationDate(),
                        parameter.getReservationTime(),
                        "COMPLETE");

        if (reservation == null) {
            return new Response(Status.NOT_FOUND_RESERVATION);
        }



        reservation.setReview(parameter.getReview());
        reservationRepository.save(reservation);

        return new Response(Status.SUCCESS_WRITE_REVIEW);
    }

    public Response deleteReview(DeleteReviewDTO parameter, String userId) {

        if (userId == null || userId.isEmpty()) {
            return new Response(Status.NOT_LOGGING_IN);
        }

        ReservationEntity reservation = reservationRepository
                .findByCustomerIdAndStoreIdAndStoreNameAndReservationDateAndReservationTimeAndReservationStatus(
                        userId, parameter.getStoreId(),
                        parameter.getStoreName(),
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
