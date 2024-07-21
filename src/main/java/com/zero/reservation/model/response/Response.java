package com.zero.reservation.model.response;

import com.zero.reservation.status.Status;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class Response {
    private final HttpStatus status;

    private final String message;

    public Response(Status responseType){
        this.status = responseType.getStatus();
        this.message = responseType.getMessage();
    }
}
