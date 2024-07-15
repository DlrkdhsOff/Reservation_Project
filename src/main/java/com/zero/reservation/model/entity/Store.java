package com.zero.reservation.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "store")
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long no;

    private String userId;
    private String userName;
    private String adminId;
    private String storeName;
    private String reservation;
    private LocalDateTime reservationDate;
    private String review;
}
