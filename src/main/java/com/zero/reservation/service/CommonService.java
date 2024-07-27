package com.zero.reservation.service;

import com.zero.reservation.model.response.Response;
import com.zero.reservation.status.Status;

public class CommonService {

    static Response checkLoginStatus(String userId) {
        if (userId == null || userId.isEmpty()) {
            return new Response(Status.NOT_LOGGING_IN);
        }
        return null;
    }
}
