package com.zero.reservation.controller;

import com.zero.reservation.model.parameter.common.LoginParameter;
import com.zero.reservation.model.parameter.common.SignUpParameters;
import com.zero.reservation.model.response.BindingResponse;
import com.zero.reservation.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j

// 일반 사용자
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpParameters request,
                                    BindingResult bindingResult) {

        log.info("request: {}", request);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(failedResult(bindingResult));
        }

        return ResponseEntity.ok(userService.signUp(request));

    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginParameter request,
                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(failedResult(bindingResult));
        }

        return ResponseEntity.ok(userService.login(request));
    }



    // 매개변수가 null일 경우
    private BindingResponse failedResult(BindingResult bindingResult) {
        BindingResponse result = new BindingResponse();
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                result = new BindingResponse(false, bindingResult.getFieldError().getDefaultMessage());
            }
        }
        return result;
    }
}
