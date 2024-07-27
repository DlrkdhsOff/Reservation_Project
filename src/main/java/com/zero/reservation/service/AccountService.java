package com.zero.reservation.service;

import com.zero.reservation.entity.UserEntity;
import com.zero.reservation.model.dto.common.LoginDTO;
import com.zero.reservation.model.dto.common.SignUpDTO;
import com.zero.reservation.model.response.Response;
import com.zero.reservation.repository.UserRepository;
import com.zero.reservation.status.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 회원가입
    public Response signUp(SignUpDTO parameter, String requestURI) {

        // 해당 아이디가 존재하는지 확인
        if(userRepository.existsByUserId(parameter.getUserId())){
            return new Response(Status.SIGNUP_FAILED_DUPLICATE_ID);
        }

        // 매개 변수로 받은 비밀번호를 암호화 하여 다시 저장
        parameter.setPassword(passwordEncoder.encode(parameter.getPassword()));

        // 일반 사용자 회원 가입일 경우
        String role = "ROLE_USER";
        boolean status = false;

        // 파트너 회원 가입일 경우
        if ("/partner-signUp".equals(requestURI)) {
            role = "ROLE_PARTNER";
            status = true;
        }

        userRepository.save(SignUpDTO.of(parameter, role));

        return new Response(status ? Status.SUCCESS_PARTNER_SIGNUP: Status.SUCCESS_SIGNUP);
    }


    // 로그인
    public Response login(LoginDTO parameter){

        // 해당 아이디가 존재하지 않을 경우
        if (!userRepository.existsByUserId(parameter.getUserId())) {
            return new Response(Status.NOT_FOUND_USER);
        }

        UserEntity user = userRepository.findByUserId(parameter.getUserId());

        String rawPassword = parameter.getPassword();

        // 입력한 비밀번호와 저장된 비밀번호가 일치하지 않을 경우
        if(!passwordEncoder.matches(rawPassword, user.getPassword())){
            return new Response(Status.PASSWORD_DOES_NOT_MATCH);
        }

        return new Response(Status.SUCCESS_LOGIN);
    }
}
