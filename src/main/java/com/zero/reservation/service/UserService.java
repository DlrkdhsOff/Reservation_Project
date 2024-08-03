package com.zero.reservation.service;

import com.zero.reservation.entity.ReservationEntity;
import com.zero.reservation.entity.StoreEntity;
import com.zero.reservation.entity.UserEntity;
import com.zero.reservation.model.dto.common.Review;
import com.zero.reservation.model.dto.common.StoreDetailDTO;
import com.zero.reservation.model.dto.common.StoreListDTO;
import com.zero.reservation.model.dto.partner.ReservationListDTO;
import com.zero.reservation.model.dto.user.*;
import com.zero.reservation.model.response.Response;
import com.zero.reservation.repository.ReservationRepository;
import com.zero.reservation.repository.StoreRepository;
import com.zero.reservation.repository.UserRepository;
import com.zero.reservation.status.ReservationStatus;
import com.zero.reservation.status.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static com.zero.reservation.model.response.DuplicateMethod.getReview;
import static com.zero.reservation.model.response.DuplicateMethod.isUserExist;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final StoreRepository storeRepository;

    private final ReservationRepository reservationRepository;


    // 매장 검색
    public Object userStoreList(UserStoreListDTO parameter, String userId) {

        // 로그인 유무, 파트너 사용자인지 확인
        Response response = isUserExist(userId, userRepository);

        // response 값이 존재 할 경우
        if (response != null) {
            return response;
        }

        // 해당 매개변수에 해당하는 데이터 저장
        List<StoreListDTO> result = getStoreList(parameter);

        if (result.isEmpty()) {
            return ResponseEntity.ok(new Response(Status.NOT_FOUND_STORE));
        }

        return result;
    }

    // 매장 상세정보
    public Object storeDetail(String userId, long storeId) {
        // 로그인 중인지 확인
        if (userId == null || userId.isEmpty()) {
            return new Response(Status.NOT_LOGGING_IN);
        }

        // 매장 정보 저장
        StoreEntity store = storeRepository.findByStoreId(storeId);
        if (store == null) {
            return new Response(Status.NOT_FOUND_STORE);
        }

        // 매장 리뷰 추출
        List<Review> reviewList = getReview(reservationRepository, store);

        return StoreDetailDTO.of(store, reviewList);
    }


    // 매장 예약
    @Transactional
    public Response reservationStore(ReservationDTO parameter, String userId) {

        Response response = isUserExist(userId, userRepository);

        if (response != null) {
            return response;
        }

        if (!storeRepository.existsByStoreId(parameter.getStoreId())) {
            return new Response(Status.FAILED_SEARCH_STORE);
        }

        // 입력한 날짜가 지난 날짜인지 확인
        Response dateTimeResponse = dateTime(parameter.getReservationDate(), parameter.getReservationTime());

        if (dateTimeResponse != null) {
            return dateTimeResponse;
        }

        UserEntity user = userRepository.findByUserId(userId);

        // 예약할 매장이 존재 하지 않을 경우
        StoreEntity store = storeRepository.findByStoreId(parameter.getStoreId());

        if (store == null) {
            return new Response(Status.NOT_FOUND_STORE);
        }

        // 동일한 예약 내역이 있을 경우
        if (reservationRepository.existsByCustomerIdAndReservationDateAndReservationTime(
                userId, parameter.getReservationDate(), parameter.getReservationTime())) {

            return new Response(Status.FAILED_RESERVATION_STORE);
        }

        reservationRepository.save(ReservationDTO.of(parameter, store, user));


        return new Response(Status.SUCCESS_RESERVATION_STORE);
    }

    // 내 모든 예약 내역
    public Object myReservation(String userId) {
        Response response = isUserExist(userId, userRepository);

        if (response != null) {
            return response;
        }

        List<ReservationListDTO> result = new ArrayList<>();
        List<ReservationEntity> list = reservationRepository.findAllByCustomerId(userId);

        // list가 null일 경우
        if (list == null || list.isEmpty()) {
            return new Response(Status.FAILED_GET_RESERVATION_LIST);
        }
        // null이 아닐경우 필요한 데이터만 result에 저장
        else {
            for (ReservationEntity entity : list) {
                result.add(ReservationListDTO.of(entity));
            }
        }
        return result;
    }


    // 키오스크 예약 확인
    @Transactional
    public Response kiosk(KioskDTO parameter, String userId) {

        Response response = isUserExist(userId, userRepository);

        if (response != null) {
            return response;
        }

        // 입력한 사용자 아이디, 매장 아이디, 예약 날짜, 에약 시간과 일치하는 데이터를 저장
        ReservationEntity reservation = reservationRepository
                .findByCustomerIdAndStoreIdAndReservationDateAndReservationTime(userId, parameter.getStoreId(),
                        parameter.getReservationDate(), parameter.getReservationTime());


        if (reservation == null) {
            return new Response(Status.NOT_FOUND_RESERVATION);
        }

        // 예약 상태에 따른 결과 반환
        response = reservationStatus(reservation);
        if (response != null) {
            return response;
        }

        return kioskDateTime(reservation);
    }

    // 리뷰 추가 및 수정
    @Transactional
    public Response review(ReviewDTO parameter, String userId) {
        return handleReview(parameter.getStoreId(),
                parameter.getReservationDate(),
                parameter.getReservationTime(),
                userId, parameter.getReview());
    }

    // 리뷰 삭제
    @Transactional
    public Response deleteReview(DeleteReviewDTO parameter, String userId) {
        return handleReview(parameter.getStoreId(),
                parameter.getReservationDate(),
                parameter.getReservationTime(),
                userId,
                null);
    }


    // 검색 내용에 따른 데이터 반환
    private List<StoreListDTO> getStoreList(UserStoreListDTO parameter) {
        List<StoreEntity> storeEntityList;

        // 매장명 검색
        if (!(parameter.getStoreName() == null || parameter.getStoreName().isEmpty())) {
            storeEntityList = storeRepository.findAllByStoreNameContaining(parameter.getStoreName());

        }
        // 매장 주소 검색
        else if (!(parameter.getStoreAddress() == null || parameter.getStoreAddress().isEmpty())) {
            storeEntityList = storeRepository.findAllByStoreAddressContaining(parameter.getStoreAddress());

        }
        // 일반 검색
        else {
            storeEntityList = storeRepository.findAllByOrderByStoreNameAsc();
        }

        // 필요한 데이터만 추출하여 result 변수에 저장
        List<StoreListDTO> result = new ArrayList<>();
        for (StoreEntity store : storeEntityList) {
            result.add(StoreListDTO.of(store));
        }

        return result;
    }


    // 지난 날짜인지 확인
    private Response dateTime(String date, String time) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            LocalDate reservationDate = LocalDate.parse(date, dateFormatter);
            LocalTime reservationTime = LocalTime.parse(time, timeFormatter);

            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now();

            // 현재 날짜보다 입력한 날짜가 전일 경우
            if (reservationDate.isBefore(today)) {
                return new Response(Status.FAILED_BEFORE_DATE);
            }
            // 현재 시간보다 이렵한 시간이 전일 경우
            else if (reservationDate.isEqual(today) && reservationTime.isBefore(now)) {
                return new Response(Status.FAILED_BEFORE_TIME);

            }
        }
        // 데이터 입력이 잘못 됐을 경우
        catch (DateTimeParseException e) {
            return new Response(Status.FAILED_DATE_FORMATTER);
        }
        return null;
    }

    // 예약 상태에 따른 결과 반환
    private Response reservationStatus(ReservationEntity reservation) {
        return switch (reservation.getReservationStatus()) {
            case "CANCEL" -> new Response(Status.RESERVATION_STATUS_CANCEL);
            case "WAITING" -> new Response(Status.RESERVATION_STATUS_WAITING);
            case "REFUSE" -> new Response(Status.RESERVATION_STATUS_REFUSE);
            case "COMPLETE" -> new Response(Status.RESERVATION_STATUS_COMPLETE);
            default -> null;
        };
    }


    // 예약 날짜가 현재 날짜 비교 후 결과 반환
    private Response kioskDateTime(ReservationEntity reservation) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        try {
            LocalDate reservationDate = LocalDate.parse(reservation.getReservationDate(), dateFormatter);
            LocalTime tenMinutesBeforeReservation = LocalTime.parse(reservation.getReservationTime(), timeFormatter)
                    .minusMinutes(10);

            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now();

            // 예약 날짜보다 현재 날짜가 전일 경우
            if (today.isBefore(reservationDate)) {
                return new Response(Status.FAILED_KIOSK_BEFORE_DATE);
            }

            // 현재 날짜보다 예약 날짜가 전일 경우
            else if (today.isAfter(reservationDate)) {
                reservation.setReservationStatus(ReservationStatus.CANCEL.toString());
                reservationRepository.save(reservation);
                return new Response(Status.FAILED_KIOSK_AFTER_DATE);
            }

            // 예약일은 정확하지만 예약 시간 10분전이 아닌 이후에 왔을 경우
            else if (reservationDate.isEqual(today) && now.isAfter(tenMinutesBeforeReservation)) {
                reservation.setReservationStatus(ReservationStatus.CANCEL.toString());
                reservationRepository.save(reservation);
                return new Response(Status.FAILED_KIOSK_AFTER_TIME);
            }

            // 입장 가능한 시간에 왔을 경우
            else {
                reservation.setReservationStatus(ReservationStatus.COMPLETE.toString());
                reservationRepository.save(reservation);
                return new Response(Status.SUCCESS_STORE_ENTRANCE);
            }
        }

        // 데이터 입력이 잘못 됐을 경우
        catch (DateTimeParseException e) {
            return new Response(Status.FAILED_DATE_FORMATTER);
        }
    }

    // 리뷰 추가, 수정, 삭제에 공통적으로 들어가는 코드를 메서드로 추출
    private Response handleReview(long storeId, String reservationDate, String reservationTime, String userId, String reviewContent) {
        Response response = isUserExist(userId, userRepository);

        if (response != null) {
            return response;
        }

        // 입력한 매장 아이디, 예약날짜, 에약 시간과 일치하며, 매장을 이용 완료한 예약 정보를 저장
        ReservationEntity reservation = reservationRepository
                .findByCustomerIdAndStoreIdAndReservationDateAndReservationTimeAndReservationStatus
                        (userId, storeId, reservationDate, reservationTime, "COMPLETE");

        if (reservation == null) {
            return new Response(Status.NOT_FOUND_RESERVATION);
        }

        // 매개 변수 reviewContent가 null이 아닐 경우 리뷰 작성 또는 수정
        // null일 경우 리뷰 삭제
        reservation.setReview(reviewContent);
        reservationRepository.save(reservation);

        if (reviewContent == null) {
            return new Response(Status.SUCCESS_DELETE_REVIEW);
        } else {
            return new Response(Status.SUCCESS_WRITE_REVIEW);
        }
    }
}
