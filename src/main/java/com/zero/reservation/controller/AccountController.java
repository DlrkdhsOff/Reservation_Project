package com.zero.reservation.controller;

import com.zero.reservation.model.dto.common.LoginDTO;
import com.zero.reservation.model.dto.common.SignUpDTO;
import com.zero.reservation.model.response.BindingResponse;
import com.zero.reservation.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j

// 일반 사용자
public class AccountController {

    private final AccountService accountService;

    // 회원가입
    @PostMapping(value = {"/signUp", "/partner-signUp"})
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpDTO parameter,
                                    BindingResult bindingResult, HttpServletRequest request) {

        log.info("parameter: {}", parameter);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(BindingResponse.failedResult(bindingResult));
        }

        return ResponseEntity.ok(accountService.signUp(parameter,  request.getRequestURI()));

    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO parameter,
                                   BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(BindingResponse.failedResult(bindingResult));
        }

        request.getSession().setAttribute("userId", parameter.getUserId());
        return ResponseEntity.ok(accountService.login(parameter));
    }
}
