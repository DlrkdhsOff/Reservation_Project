package com.zero.reservation.service;

import com.zero.reservation.model.entity.Member;
import com.zero.reservation.model.param.Response;
import com.zero.reservation.model.dto.MemberDTO;
import com.zero.reservation.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Response createAccount(MemberDTO memberDTO, String requestURI) {

        if (accountRepository.existsByEmail(memberDTO.getEmail())) {
            return new Response(false, "이미 존재하는 회원입니다.");
        }

        Member member = accountRepository.save(Member.builder()
                .email(memberDTO.getEmail())
                .password(memberDTO.getPassword())
                .userName(memberDTO.getName())
                .tel(memberDTO.getTel())
                .joinDate(LocalDate.now())
                .isPartner("/create/partner-account".equals(requestURI))
                .build());

        String message;
        if (member.isPartner()) {
            message = "파트너 회원 가입에 성공하였습니다.";
        } else {
            message = "회원 가입에 성공하였습니다.";
        }

        return new Response(true, message);
    }

    public Response login(String email, String password) {
        Member result = accountRepository.findByEmail(email);

        if (result == null) {
            return new Response(false, "존재하지 않은 회원 입니다.");
        }

        if (!(password.equals(result.getPassword()))) {
            return new Response(false, "비밀번호가 일치 하지 않습니다.");
        }

        return new Response(true, "로그인 하였습니다.");
    }

}