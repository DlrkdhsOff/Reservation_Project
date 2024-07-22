package com.zero.reservation.controller;

import com.zero.reservation.model.dto.partner.StoreListDTO;
import com.zero.reservation.model.dto.user.UserStoreListDTO;
import com.zero.reservation.model.response.Response;
import com.zero.reservation.service.UserService;
import com.zero.reservation.status.Status;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
}
