//package com.zero.reservation.service.impl;
//
//import com.zero.reservation.domain.Store;
//import com.zero.reservation.domain.User;
//import com.zero.reservation.dto123.request.store.StoreRegisterRequest;
//import com.zero.reservation.dto123.request.store.StoreRemoveRequest;
//import com.zero.reservation.dto123.request.store.StoreUpdateRequest;
//import com.zero.reservation.dto123.response.store.*;
//import com.zero.reservation.repository.StoreRepository;
//import com.zero.reservation.repository.UserRepository;
//import com.zero.reservation.service.StoreService;
//import com.zero.reservation.type.ResponseType;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Slf4j
//@RequiredArgsConstructor
//@Service
//public class StoreServiceImpl implements StoreService {
//
//    private final StoreRepository storeRepository;
//    private final UserRepository userRepository;
//
//    @Override
//    public StoreListResponse getStoreList() {
//        return new StoreListResponse(StoreResponse.list(storeRepository.findAllByDeletedAtIsNull()));
//    }
//
//    @Transactional
//    @Override
//    public StoreRegisterResponse addStore(StoreRegisterRequest request) {
//
//        try {
//            User user = userRepository.findById(request.getUserId()).orElseThrow(
//                    () -> new IllegalArgumentException("해당 유저가 없습니다."));
//            Store store = Store.builder()
//                    .name(request.getStoreName())
//                    .address(request.getStoreAddress())
//                    .phoneNumber(request.getStorePn())
//                    .description(request.getStoreDesc())
//                    .maxCapacity(request.getStoreCA())
//                    .manager(user)
//                    .build();
//
//            storeRepository.save(store);
//        } catch (Exception e) {
//            return new StoreRegisterResponse(ResponseType.STORE_REGISTER_FAIL);
//        }
//        return new StoreRegisterResponse(ResponseType.STORE_REGISTER_SUCCESS);
//    }
//
//    @Transactional
//    @Override
//    public StoreUpdateResponse updateStore(StoreUpdateRequest request) {
//
//        try {
//            Long storeId = request.getStoreId();
//
//            Store store = storeRepository.findById(storeId).orElseThrow(
//                    () -> new IllegalArgumentException("해당 가게가 없습니다.")
//            );
//
//            store.update(request);
//        } catch (Exception e) {
//            log.warn(e.getMessage());
//            return new StoreUpdateResponse(ResponseType.STORE_EDIT_FAIL);
//        }
//
//        return new StoreUpdateResponse(ResponseType.STORE_EDIT_SUCCESS);
//    }
//
//    @Transactional
//    @Override
//    public StoreRemoveResponse removeStore(StoreRemoveRequest request) {
//
//        try {
//            Long storeId = request.getStoreId();
//            storeRepository.deleteByIdAndDeletedAtIsNull(storeId);
//        } catch (Exception e) {
//            log.warn(e.getMessage());
//            return new StoreRemoveResponse(ResponseType.STORE_DELETE_FAIL);
//        }
//        return new StoreRemoveResponse(ResponseType.STORE_DELETE_SUCCESS);
//    }
//}
