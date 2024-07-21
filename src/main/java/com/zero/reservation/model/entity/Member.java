package com.zero.reservation.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Entity(name = "member")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    private String userId;          // 사용자 아이디

    private String password;        // 사용자 비밀번호
    private String userName;        // 사용자 이름
    private String tel;             // 사용자 전화번호
    private LocalDate joinDate;     // 회원기입일
    private String Role;            // 일반 사용자 파트너 여부
}
