package com.zero.reservation.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    private String userId;

    private String password;
    private String name;
    private String tel;
    private LocalDate joinDate;
    private boolean isAdmin;

}
