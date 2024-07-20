package com.zero.reservation.controller;

import com.zero.reservation.model.param.Response;
import com.zero.reservation.model.dto.PartnerDTO;
import com.zero.reservation.service.PartnerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partner")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;

    @GetMapping("/add-store")
    public ResponseEntity<?> addStore(HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");

        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.ok().body(new Response(false, "로그인을 해주세요"));
        }

        Response result = partnerService.checkAdmin(userId);

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/add-store")
    public ResponseEntity<Response> addStore(@Valid @RequestBody PartnerDTO partnerDTO,
                                             HttpServletRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("AccountController.createAccount");
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                Response result = new Response(false, bindingResult.getFieldError().getDefaultMessage());
                return ResponseEntity.ok().body(result);
            }
        }
        String userId = (String) request.getSession().getAttribute("userId");
        Response result = partnerService.addStore(partnerDTO, userId);

        return ResponseEntity.ok().body(result);
    }
}
