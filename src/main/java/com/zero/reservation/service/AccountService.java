package com.zero.reservation.service;

import com.zero.reservation.model.dto.MemberDTO;
import com.zero.reservation.model.entity.Member;
import com.zero.reservation.model.param.Response;
import com.zero.reservation.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;
    private final DataSourcePoolMetadataProvider hikariPoolDataSourceMetadataProvider;

    public Response createAccount(MemberDTO memberDTO, String requestURI) {

        if (accountRepository.existsByUserId(memberDTO.getUserId())) {
            return new Response(false, "이미 존재하는 회원입니다.");
        }

        String password = passwordEncoder.encode(memberDTO.getPassword());
        String role = "ROLE_USER";
        if ("/create/partner-account".equals(requestURI)) {
            role = "ROLE_PARTNER";
        }

        Member member = accountRepository.save(Member.builder()
                .userId(memberDTO.getUserId())
                .password(password)
                .userName(memberDTO.getName())
                .tel(memberDTO.getTel())
                .joinDate(LocalDate.now())
                .Role(role)
                .build());

        String message;
        if (member.getRole().equals("ROLE_PARTNER")) {
            message = "파트너 회원 가입에 성공하였습니다.";
        } else {
            message = "회원 가입에 성공하였습니다.";
        }

        return new Response(true, message);
    }

//    public Response login(String email, String password) {
//        Optional<Member> optionalMember = accountRepository.findByEmail(email);
//
//        if (optionalMember.isEmpty()) {
//            return new Response(false, "존재하지 않은 회원 입니다.");
//        }
//
//        Member member = optionalMember.get();
//
//
//        if (!passwordEncoder.matches(password, member.getPassword())) {
//            return new Response(false, "비밀번호가 일치 하지 않습니다.");
//        }
//
//        return new Response(true, "로그인 하였습니다.");
//    }

}