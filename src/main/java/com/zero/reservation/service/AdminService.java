package com.zero.reservation.service;

import com.zero.reservation.model.Response;
import com.zero.reservation.model.entity.Member;
import com.zero.reservation.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AccountRepository accountRepository;

    public Response addStore(String userId) {
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
}
