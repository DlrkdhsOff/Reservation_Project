package com.zero.reservation.service;

import com.zero.reservation.model.dto.CustomUserDetailsDTO;
import com.zero.reservation.model.entity.Member;
import com.zero.reservation.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("AccountService.loadUserByUsername");

        Member member = accountRepository.findByUserId(username);

        if (member == null) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        return new CustomUserDetailsDTO(member);
    }
}
