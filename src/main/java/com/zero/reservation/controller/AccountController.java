package com.zero.reservation.controller;

import com.zero.reservation.model.param.Response;
import com.zero.reservation.model.dto.MemberDTO;
import com.zero.reservation.model.param.AccountParam;
import com.zero.reservation.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping(value = {"/create/account", "/create/partner-account"})
    public ResponseEntity<Response> createAccount(@Valid @RequestBody MemberDTO memberDTO,
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
                Response result = new Response(false, bindingResult.getFieldError().getDefaultMessage());
                return ResponseEntity.ok().body(result);
            }
        }

        Response result = accountService.login(accountParam.getEmail(), accountParam.getPassword());

        if (result.isResult()) {
            request.getSession().setAttribute("email",accountParam.getEmail());
        }

        return ResponseEntity.ok().body(result);
    }


}
