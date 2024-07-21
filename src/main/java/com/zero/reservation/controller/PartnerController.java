package com.zero.reservation.controller;

import com.zero.reservation.entity.StoreEntity;
import com.zero.reservation.model.dto.partner.StoreDTO;
import com.zero.reservation.model.dto.partner.UpdateStoreDTO;
import com.zero.reservation.model.response.*;
import com.zero.reservation.service.*;
import com.zero.reservation.status.Status;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/partner")
@Slf4j

// 파트너
public class PartnerController {

    private final AccountService accountService;

    private final PartnerService partnerService;


    @PostMapping("add")
    public ResponseEntity<?> addStore(@RequestBody @Valid StoreDTO parameter,
                                      BindingResult bindingResult, HttpServletRequest request) {

        log.info("parameter: {}", parameter);
        failedResult(bindingResult);

        String userId = (String) request.getSession().getAttribute("userId");

        Response result = partnerService.addStore(parameter, userId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("store-list")
    public ResponseEntity<?> getStoreList(HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");

        List<StoreEntity> list = partnerService.getStoreList(userId);
        if (list.isEmpty()) {
            return ResponseEntity.ok(Status.NOT_FOUND_STORE);
        }
        return ResponseEntity.ok(list);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateStore(@RequestBody @Valid UpdateStoreDTO parameter,
                                         BindingResult bindingResult, HttpServletRequest request) {

        failedResult(bindingResult);

        String userId = (String) request.getSession().getAttribute("userId");

        return ResponseEntity.ok(partnerService.updateStore(parameter, userId));
    }

//    @GetMapping("/user/list")
//    public ResponseEntity<StoreListResponse> getStoreList(){
//        return ResponseEntity.ok(storeService.getStoreList());
//    }
//
//    @DeleteMapping("/remove")
//    public ResponseEntity<StoreRemoveResponse> removeStore(@RequestBody @Valid StoreRemoveRequest request){
//        return ResponseEntity.ok(storeService.removeStore(request));
//    }



    // 매개변수가 null일 경우
    private BindingResponse failedResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasErrors()) {
                FieldError fieldError = bindingResult.getFieldError();
                if (fieldError != null) {
                    return new BindingResponse(false, bindingResult.getFieldError().getDefaultMessage());
                }
            }
        }

        return null;
    }
}
