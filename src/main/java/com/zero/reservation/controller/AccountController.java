package com.zero.reservation.controller;

import com.zero.reservation.model.Response;
import com.zero.reservation.model.dto.MemberDTO;
import com.zero.reservation.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    @GetMapping(value = {"/create/account", "/create/admin-account"})
    public ResponseEntity<String> createAccount(HttpServletRequest request) {
        String message = "회원 가입하기\n아이디 비밀번호 이름 전화번호를 입력해주세요";

        if ("/create/admin-account".equals(request.getRequestURI())) {
            message = "파트너 회원 가입하기\n아이디 비밀번호 이름 전화번호를 입력해주세요";
        }

        return ResponseEntity.ok().body(message);
    }

    @PostMapping(value = {"/create/account", "/create/admin-account"})
    public ResponseEntity<?> createAccount(@Valid @RequestBody MemberDTO memberDTO,
                                           HttpServletRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                return ResponseEntity.ok().body(fieldError.getDefaultMessage());
            }
        }

        Response result = accountService.createAccount(memberDTO, request.getRequestURI());
        return ResponseEntity.ok().body(result.getMessage());
    }
}
