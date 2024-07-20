package com.zero.reservation.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MemberDTO {

    @NotNull(message = "아이디를 입력해주세요")
    private String email;

    @NotNull(message = "비밀번호를 입력해주세요")
    private String password;

    @NotNull(message = "이름을 입력해주세요")
    private String name;

    @NotNull(message = "전화번호를 입력해주세요")
    private String tel;
}

