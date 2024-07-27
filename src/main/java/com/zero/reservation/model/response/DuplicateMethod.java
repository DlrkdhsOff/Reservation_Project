package com.zero.reservation.model.response;

import com.zero.reservation.entity.UserEntity;
import com.zero.reservation.repository.UserRepository;
import com.zero.reservation.status.Status;

// 로그인 여부와 해당 계정이 파트너 및 일반 사용자 확인
public class DuplicateMethod {

    public static Response isPartnerExist(String userId, UserRepository userRepository) {

        if (userId == null || userId.isEmpty()) {
            return new Response(Status.NOT_LOGGING_IN);
        }

        UserEntity user = userRepository.findByUserId(userId);

        if (user.getRole().equals("ROLE_USER")) {
            return new Response(Status.NOT_PARTNER_USER);
        }

        return null;
    }

    public static Response isUserExist(String userId, UserRepository userRepository) {

        if (userId == null || userId.isEmpty()) {
            return new Response(Status.NOT_LOGGING_IN);
        }

        UserEntity user = userRepository.findByUserId(userId);

        if (user.getRole().equals("ROLE_PARTNER")) {
            return new Response(Status.IS_PARTNER_USER);
        }

        return null;
    }
}
