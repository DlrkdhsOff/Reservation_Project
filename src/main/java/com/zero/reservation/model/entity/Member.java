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
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String email;           // 사용자 이메일
    private String password;        // 사용자 비밀번호
    private String userName;        // 사용자 이름
    private String tel;             // 사용자 전화번호
    private LocalDate joinDate;     // 회원기입일
    private boolean isPartner;      // 일반 사용자 파트너 여부
}
