package com.zero.reservation.model.param;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
public class AccountParam {

    @NotNull(message = "아이디를 입력해주세요")
    private String userId;

    @NotNull(message = "비밀번호를 입력해주세요")
    private String password;
}
