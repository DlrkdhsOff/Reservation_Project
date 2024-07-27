package com.zero.reservation.model.response;

import com.zero.reservation.entity.ReservationEntity;
import com.zero.reservation.entity.StoreEntity;
import com.zero.reservation.entity.UserEntity;
import com.zero.reservation.model.dto.common.Review;
import com.zero.reservation.repository.ReservationRepository;
import com.zero.reservation.repository.UserRepository;
import com.zero.reservation.status.Status;

import java.util.ArrayList;
import java.util.List;

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


    // 매장 리뷰 목록
    public static List<Review> getReview(ReservationRepository reservationRepository, StoreEntity store) {
        List<ReservationEntity> reservationEntityList = reservationRepository.findAllByStoreId(store.getStoreId());

        List<Review> reviews = new ArrayList<>();
        for (ReservationEntity r : reservationEntityList) {
            Review review = new Review();
            if (r.getReview() == null) {
                continue;
            }
            review.setUserName(r.getUserName());
            review.setReview(r.getReview());
            reviews.add(review);
        }

        return reviews;
    }
}
