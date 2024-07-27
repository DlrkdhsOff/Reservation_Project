package com.zero.reservation.controller;

import com.zero.reservation.model.dto.common.StoreListDTO;
import com.zero.reservation.model.dto.partner.*;
import com.zero.reservation.model.response.BindingResponse;
import com.zero.reservation.model.response.Response;
import com.zero.reservation.service.PartnerService;
import com.zero.reservation.status.Status;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/partner")
@Slf4j

// 파트너
public class PartnerController {

    private final PartnerService partnerService;


    // 매장 등록
    @PostMapping("add")
    public ResponseEntity<?> addStore(@RequestBody @Valid AddStoreDTO parameter,
                                      BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(BindingResponse.failedResult(bindingResult));
        }

        // 세션에 저장된 아이디를 userId 변수에 저장
        String userId = (String) request.getSession().getAttribute("userId");

        return ResponseEntity.ok(partnerService.addStore(parameter, userId));
    }

    // 매장 목록
    @GetMapping("store-list")
    public ResponseEntity<?> getStoreList(HttpServletRequest request) {

        String userId = (String) request.getSession().getAttribute("userId");

        return ResponseEntity.ok(partnerService.getStoreList(userId));
    }


    // 등록한 매장 수정
    @PatchMapping("/update")
    public ResponseEntity<?> updateStore(@RequestBody @Valid UpdateStoreDTO parameter,
                                         BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(BindingResponse.failedResult(bindingResult));
        }

        String userId = (String) request.getSession().getAttribute("userId");

        return ResponseEntity.ok(partnerService.updateStore(parameter, userId));
    }


    // 매장 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<?> removeStore(@RequestBody @Valid DeleteStoreDTO parameter, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");

        return ResponseEntity.ok(partnerService.deleteStore(parameter, userId));
    }

    // 매장 예약 목록
    @GetMapping("/reservation-list")
    public ResponseEntity<?> reservationList(HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");

        return ResponseEntity.ok(partnerService.getReservationList(userId));
    }

    // 매장 예약 승인 / 거절
    @PostMapping("/reservation-approve/{status}")
    public ResponseEntity<?> reservationApprove(@PathVariable String status,
                                                @RequestBody @Valid ReservationApproveDTO parameter,
                                                BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(BindingResponse.failedResult(bindingResult));
        }
        String userId = (String) request.getSession().getAttribute("userId");

        return ResponseEntity.ok(partnerService.reservationApprove(status, parameter, userId));
    }


    // 매장 리뷰 삭제
    @DeleteMapping("/delete-review")
    public ResponseEntity<?> deleteReview(@RequestBody @Valid DeleteReviewDTO parameter,
                                          BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(BindingResponse.failedResult(bindingResult));
        }

        String userId = (String) request.getSession().getAttribute("userId");
        return ResponseEntity.ok(partnerService.deleteReview(parameter, userId));
    }
}
