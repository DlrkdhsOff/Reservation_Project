package com.zero.reservation.service;

import com.zero.reservation.entity.StoreEntity;
import com.zero.reservation.entity.UserEntity;
import com.zero.reservation.model.dto.partner.StoreDTO;
import com.zero.reservation.model.response.Response;
import com.zero.reservation.repository.PartnerRepository;
import com.zero.reservation.repository.UserRepository;
import com.zero.reservation.status.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class PartnerService {

    private final PartnerRepository partnerRepository;

    private final UserRepository userRepository;

    public Response addStore(StoreDTO parameter, String userId) {

        if (userId.isBlank()) {
            return new Response(Status.NOT_LOGGING_IN);
        }

        UserEntity user = userRepository.findByUserId(userId);

        if (user.getRole().equals("ROLE_USER")) {
            return new Response(Status.NOT_PARTNER_USER);
        }

        StoreEntity store = partnerRepository.save(StoreDTO.of(parameter, userId, user.getUserName()));

        return new Response(Status.SUCCESS_ADD_STORE);
    }
}
