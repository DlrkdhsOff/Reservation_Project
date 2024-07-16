package com.zero.reservation.service;

import com.zero.reservation.model.Response;
import com.zero.reservation.model.dto.AdminDTO;
import com.zero.reservation.model.entity.Admin;
import com.zero.reservation.model.entity.Member;
import com.zero.reservation.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AccountRepository accountRepository;

    private final AdminRepository adminRepository;

    public Response checkAdmin(String userId) {
        Optional<Member> optionalMember = accountRepository.findById(userId);

        Member member = optionalMember.get();

        boolean result = false;
        String message;
        if (member.isAdmin()) {
            result = true;
            message = "등록하실 매장 명, 상점 위치, 상점 상세 정보를 입력하세요.";
        } else {
            message = "파트너 회원이 아닙니다 파트너 회원 가입을 해주세요.";
        }

        return new Response(result, message);
    }


    public Response addStore(AdminDTO adminDTO, String userId) {

        if (adminRepository.existsByUserIdAndStoreName(userId, adminDTO.getStoreName())) {
            return new Response(false, "이미 등록한 매장입니다.");
        }

        Admin admin = adminRepository.save(Admin.builder()
                .userId(userId)
                .storeName(adminDTO.getStoreName())
                .storeAddress(adminDTO.getStoreAddress())
                .storeInfo(adminDTO.getStoreInfo())
                .registrationDate(LocalDate.now())
                .build());

        return new Response(true, "매장을 등록하였습니다.");
    }

}
