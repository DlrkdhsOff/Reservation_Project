package com.zero.reservation.service;

import com.zero.reservation.model.Response;
import com.zero.reservation.model.dto.MemberDTO;
import com.zero.reservation.model.entity.Member;
import com.zero.reservation.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;


    public Response createAccount(MemberDTO memberDTO) {
        if (accountRepository.existsByUserId(memberDTO.getUserId())) {
            return new Response(false, "이미 존재하는 회원입니다.");
        }

        accountRepository.save(Member.builder()
                .userId(memberDTO.getUserId())
                .password(memberDTO.getPassword())
                .name(memberDTO.getName())
                .tel(memberDTO.getTel())
                .joinDate(LocalDate.now())
                .isAdmin(false)
                .build());

        return new Response(true, "회원 가입에 성공하였습니다.");
    }
}
