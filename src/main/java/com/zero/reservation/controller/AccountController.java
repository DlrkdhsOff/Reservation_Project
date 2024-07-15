package com.zero.reservation.controller;

import com.zero.reservation.model.Response;
import com.zero.reservation.model.dto.MemberDTO;
import com.zero.reservation.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create/account")
    public ResponseEntity<?> createAccount(@Valid @RequestBody MemberDTO memberDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                return ResponseEntity.ok().body(fieldError.getDefaultMessage());
            }
        }

        Response result = accountService.createAccount(memberDTO);
        return ResponseEntity.ok().body(result.getMessage());
    }
}
