package com.zero.reservation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserEntity {

    @Id
    private String userId;          // 사용자 아이디

    private String password;        // 비밀번호
    private String userName;        // 사용자 이름
    private String tel;             // 전화번호
    private LocalDateTime regDt;    // 회원가입일
    private String role;            // 일반 사용자 파트너 구분


//    @OneToMany(mappedBy="manager")
//    private List<Store> stores;
//
//    @OneToMany(mappedBy="user")
//    private List<Reservation> reservations;
}
