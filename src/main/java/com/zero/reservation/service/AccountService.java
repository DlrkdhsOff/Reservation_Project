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

    public Response signUp(SignUpDTO parameter, String requestURI) {

        if(userRepository.existsByUserId(parameter.getUserId())){
            return new Response(Status.SIGNUP_FAILED_DUPLICATE_ID);
        }
        log.info("request: {}", parameter);


        parameter.setPassword(passwordEncoder.encode(parameter.getPassword()));

        String role = "ROLE_USER";
        boolean status = false;
        if ("/partner-signUp".equals(requestURI)) {
            role = "ROLE_PARTNER";
            status = true;
        }

        userRepository.save(SignUpDTO.of(parameter, role));

        return new Response(status ? Status.SUCCESS_PARTNER_SIGNUP: Status.SUCCESS_SIGNUP);
    }


    public Response login(LoginDTO parameter){

        UserEntity user = userRepository.findByUserId(parameter.getUserId());

        if (user == null) {
            return new Response(Status.FAILED_LOGIN_NOT_FOUND_USER);
        }

        String rawPassword = parameter.getPassword();

        if(!passwordEncoder.matches(rawPassword, user.getPassword())){
            return new Response(Status.FAILED_LOGIN_PASSWORD_DOES_NOT_MATCH);
        }

        return new Response(Status.SUCCESS_LOGIN);
    }
}
