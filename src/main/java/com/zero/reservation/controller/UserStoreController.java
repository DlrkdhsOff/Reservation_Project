//package com.zero.reservation.controller;
//
//import com.zero.reservation.dto123.request.store.StoreRegisterRequest;
//import com.zero.reservation.dto123.request.store.StoreRemoveRequest;
//import com.zero.reservation.dto123.request.store.StoreUpdateRequest;
//import com.zero.reservation.dto123.response.store.StoreListResponse;
//import com.zero.reservation.dto123.response.store.StoreRegisterResponse;
//import com.zero.reservation.dto123.response.store.StoreRemoveResponse;
//import com.zero.reservation.dto123.response.store.StoreUpdateResponse;
//import com.zero.reservation.service.StoreService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
///**
// * 사용자가 사용하는 컨트롤러
// */
//@RestController
//@RequestMapping("/api/store")
//@RequiredArgsConstructor
//public class UserStoreController {
//
//    private final StoreService storeService;
//
//    @GetMapping("/user/list")
//    public ResponseEntity<StoreListResponse> getStoreList(){
//        return ResponseEntity.ok(storeService.getStoreList());
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<StoreRegisterResponse> registerStore(@RequestBody @Valid StoreRegisterRequest request){
//        return ResponseEntity.ok(storeService.addStore(request));
//    }
//
//    @PatchMapping("/update")
//    public ResponseEntity<StoreUpdateResponse> updateStore(@RequestBody @Valid StoreUpdateRequest request){
//        return ResponseEntity.ok(storeService.updateStore(request));
//    }
//
//    @DeleteMapping("/remove")
//    public ResponseEntity<StoreRemoveResponse> removeStore(@RequestBody @Valid StoreRemoveRequest request){
//        return ResponseEntity.ok(storeService.removeStore(request));
//    }
//}
