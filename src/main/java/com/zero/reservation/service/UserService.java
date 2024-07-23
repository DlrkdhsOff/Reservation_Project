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


}
