package com.zero.reservation.model.parameter.common;

import com.zero.reservation.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SignUpParameters {

    @NotNull(message = "아이디를 입력해주세요")
    private String userId;

    @NotNull(message = "비밀번호를 입력해주세요")
    private String password;

    @NotNull(message = "이름을 입력해주세요")
    private String userName;

    @NotNull(message = "전화번호를 입력해주세요")
    private String tel;

    public static UserEntity of(SignUpParameters request, String role) {
        return UserEntity.builder()
                .userId(request.getUserId())
                .password(request.getPassword())
                .userName(request.getUserName())
                .tel(request.getTel())
                .regDt(LocalDateTime.now())
                .role(role)
                .build();
    }
}
