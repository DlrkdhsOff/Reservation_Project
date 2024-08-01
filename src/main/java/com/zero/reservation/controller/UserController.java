package com.zero.reservation.controller;

import com.zero.reservation.model.dto.user.*;
import com.zero.reservation.model.response.BindingResponse;
import com.zero.reservation.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;


    // 검색한 데이터에 따른 매장 목록
    @PostMapping("/store-list")
    public ResponseEntity<?> getStoreList(@RequestBody @Valid UserStoreListDTO parameter,
                                          HttpServletRequest request) {

        log.info("parameter: {}", parameter);
        String userId = (String) request.getSession().getAttribute("userId");

        return ResponseEntity.ok(userService.userStoreList(parameter, userId));
    }

    // 매장 상제 정보
    @GetMapping("/store-list/detail/{storeId}")
    public ResponseEntity<?> getStoreListDetail(@PathVariable long storeId,
                                                HttpServletRequest request) {

        String userId = (String) request.getSession().getAttribute("userId");
        return ResponseEntity.ok(userService.storeDetail(userId, storeId));
    }


    // 매장 예약
    @PostMapping("/reservation")
    public ResponseEntity<?> reservation(@RequestBody @Valid ReservationDTO parameter,
                                         BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(BindingResponse.failedResult(bindingResult));
        }

        String userId = (String) request.getSession().getAttribute("userId");

        return ResponseEntity.ok(userService.reservationStore(parameter, userId));
    }

    // 내 모든 예약 내역
    @GetMapping("/myReservation")
    public ResponseEntity<?> myReservation(HttpServletRequest request) {

        String userId = (String) request.getSession().getAttribute("userId");
        return ResponseEntity.ok(userService.myReservation(userId));
    }


    // 키오스크에서 예약 확인
    @GetMapping("/kiosk")
    public ResponseEntity<?> kiosk(@RequestBody @Valid KioskDTO parameter,
                                   BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(BindingResponse.failedResult(bindingResult));
        }

        String userId = (String) request.getSession().getAttribute("userId");
        return ResponseEntity.ok(userService.kiosk(parameter, userId));
    }


    // 리뷰 작성
    @PostMapping("/review")
    public ResponseEntity<?> review(@RequestBody @Valid ReviewDTO parameter,
                                    BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(BindingResponse.failedResult(bindingResult));
        }

        String userId = (String) request.getSession().getAttribute("userId");
        return ResponseEntity.ok(userService.review(parameter, userId));

    }


    // 리뷰 수정
    @PatchMapping("/update-review")
    public ResponseEntity<?> updateReview(@RequestBody @Valid ReviewDTO parameter,
                                          BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(BindingResponse.failedResult(bindingResult));
        }

        String userId = (String) request.getSession().getAttribute("userId");
        return ResponseEntity.ok(userService.review(parameter, userId));

    }


    // 리뷰 삭제
    @DeleteMapping("/delete-review")
    public ResponseEntity<?> deleteReview(@RequestBody @Valid DeleteReviewDTO parameter,
                                          BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(BindingResponse.failedResult(bindingResult));
        }

        String userId = (String) request.getSession().getAttribute("userId");
        return ResponseEntity.ok(userService.deleteReview(parameter, userId));

    }
}
