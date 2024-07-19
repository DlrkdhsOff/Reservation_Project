package com.zero.reservation.service;

import com.zero.reservation.model.dto.UserDTO;
import com.zero.reservation.model.entity.Partner;
import com.zero.reservation.repository.PartnerRepository;
import com.zero.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public final PartnerRepository partnerRepository;

    public List<UserDTO> getStoreList() {
        List<UserDTO> result = new ArrayList<>();

        for (Partner p : partnerRepository.findAll()) {
            UserDTO dto = new UserDTO();
            result.add(dto.of(p));
        }
        return result;
    }
}
