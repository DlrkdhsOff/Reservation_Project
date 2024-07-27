package com.zero.reservation.controller;

import com.zero.reservation.model.dto.user.DeleteReviewDTO;
import com.zero.reservation.model.dto.common.StoreListDTO;
import com.zero.reservation.model.dto.user.KioskDTO;
import com.zero.reservation.model.dto.user.ReservationDTO;
import com.zero.reservation.model.dto.user.ReviewDTO;
import com.zero.reservation.model.dto.user.UserStoreListDTO;
import com.zero.reservation.model.response.BindingResponse;
import com.zero.reservation.model.response.Response;
import com.zero.reservation.service.UserService;
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
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/store-list")
    public ResponseEntity<?> getStoreList(@RequestBody @Valid UserStoreListDTO parameter,
                                          HttpServletRequest request) {

        log.info("parameter: {}", parameter);
        String userId = (String) request.getSession().getAttribute("userId");
        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.ok(new Response(Status.NOT_LOGGING_IN));
        }

        List<StoreListDTO> list = userService.getUserStoreList(parameter, userId);
        if (list.isEmpty()) {
            return ResponseEntity.ok(new Response(Status.NOT_FOUND_STORE));
        }
        return ResponseEntity.ok(list);
    }

    @PostMapping("/reservation")
    public ResponseEntity<?> reservation(@RequestBody @Valid ReservationDTO parameter,
                                         BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(BindingResponse.failedResult(bindingResult));
        }

        String userId = (String) request.getSession().getAttribute("userId");
        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.ok(new Response(Status.NOT_LOGGING_IN));
        }

        return ResponseEntity.ok(userService.reservationStore(parameter, userId));
    }

    @GetMapping("/check-reservation")
    public ResponseEntity<?> checkReservation(@RequestBody @Valid KioskDTO parameter,
                                              BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(BindingResponse.failedResult(bindingResult));
        }

        String userId = (String) request.getSession().getAttribute("userId");
        return ResponseEntity.ok(userService.checkReservation(parameter, userId));
    }

    @PostMapping("/review")
    public ResponseEntity<?> review(@RequestBody @Valid ReviewDTO parameter,
                                    BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(BindingResponse.failedResult(bindingResult));
        }

        String userId = (String) request.getSession().getAttribute("userId");
        return ResponseEntity.ok(userService.review(parameter, userId));

    }

    @PostMapping("/update-review")
    public ResponseEntity<?> updateReview(@RequestBody @Valid ReviewDTO parameter,
                                          BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(BindingResponse.failedResult(bindingResult));
        }

        String userId = (String) request.getSession().getAttribute("userId");
        return ResponseEntity.ok(userService.review(parameter, userId));

    }

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
