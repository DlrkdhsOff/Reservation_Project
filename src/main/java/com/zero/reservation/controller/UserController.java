package com.zero.reservation.controller;

import com.zero.reservation.model.Response;
import com.zero.reservation.model.dto.UserDTO;
import com.zero.reservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/search-store")
    public ResponseEntity<?> searchStore() {
        List<UserDTO> list = userService.getStoreList();

        if (list.isEmpty()) {
            return ResponseEntity.ok().body(new Response(false, "등록된 매장이 없습니다."));
        }

        return ResponseEntity.ok(list);
    }

}
