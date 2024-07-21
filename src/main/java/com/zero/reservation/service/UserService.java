package com.zero.reservation.service;

import com.zero.reservation.entity.UserEntity;
import com.zero.reservation.model.parameter.common.LoginParameter;
import com.zero.reservation.model.parameter.common.SignUpParameters;
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
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Response signUp(SignUpParameters request) {

        if(userRepository.existsByUserId(request.getUserId())){
            return new Response(Status.SIGNUP_FAILED_DUPLICATE_ID);
        }
        log.info("request: {}", request);


        request.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(SignUpParameters.of(request, "ROLE_USER"));

        return new Response(Status.SUCCESS_SIGNUP);
    }

    public Response login(LoginParameter request){

        UserEntity user = userRepository.findByUserId(request.getUserId());

        if (user == null) {
            return new Response(Status.FAILED_LOGIN_NOT_FOUND_USER);
        }

        String rawPassword = request.getPassword();

        if(!passwordEncoder.matches(rawPassword, user.getPassword())){
            return new Response(Status.FAILED_LOGIN_PASSWORD_DOES_NOT_MATCH);
        }

        return new Response(Status.SUCCESS_LOGIN);
    }
}
