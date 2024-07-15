package com.zero.reservation.controller;

import com.zero.reservation.model.Response;
import com.zero.reservation.model.dto.MemberDTO;
import com.zero.reservation.model.param.AccountParam;
import com.zero.reservation.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping(value = {"/create/account", "/create/admin-account"})
    public ResponseEntity<?> createAccount(@Valid @RequestBody MemberDTO memberDTO,
                                           HttpServletRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                Response result = new Response(false, bindingResult.getFieldError().getDefaultMessage());
                return ResponseEntity.ok().body(result);
            }
        }

        Response result = accountService.createAccount(memberDTO, request.getRequestURI());
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@Valid @RequestBody AccountParam accountParam,
                                           HttpServletRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                Response result = new Response(false, fieldError.getDefaultMessage());
                System.out.println("fieldError.getDefaultMessage() = " + fieldError.getDefaultMessage());
                return ResponseEntity.ok().body(result);
            }
        }

        Response result = accountService.login(accountParam.getUserId(), accountParam.getPassword());

        if (result.isResult()) {
            request.getSession().setAttribute("userId",accountParam.getUserId());
        }

        return ResponseEntity.ok().body(result);
    }


}
