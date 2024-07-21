package com.zero.reservation.controller;

import com.zero.reservation.model.dto.partner.StoreDTO;
import com.zero.reservation.model.response.BindingResponse;
import com.zero.reservation.model.response.Response;
import com.zero.reservation.service.AccountService;
import com.zero.reservation.service.PartnerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/partner")
@Slf4j

// 파트너
public class PartnerController {

    private final AccountService accountService;

    private final PartnerService partnerService;


    @PostMapping("add-store")
    public ResponseEntity<?> addStore(@RequestBody @Valid StoreDTO parameter,
                                      BindingResult bindingResult, HttpServletRequest request) {

        log.info("parameter: {}", parameter);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(failedResult(bindingResult));
        }

        String userId = (String) request.getSession().getAttribute("userId");

        Response result = partnerService.addStore(parameter, userId);

        return ResponseEntity.ok(result);
    }

//    @GetMapping("/user/list")
//    public ResponseEntity<StoreListResponse> getStoreList(){
//        return ResponseEntity.ok(storeService.getStoreList());
//    }
//    @PatchMapping("/update")
//    public ResponseEntity<StoreUpdateResponse> updateStore(@RequestBody @Valid StoreUpdateRequest request){
//        return ResponseEntity.ok(storeService.updateStore(request));
//    }
//
//    @DeleteMapping("/remove")
//    public ResponseEntity<StoreRemoveResponse> removeStore(@RequestBody @Valid StoreRemoveRequest request){
//        return ResponseEntity.ok(storeService.removeStore(request));
//    }



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
