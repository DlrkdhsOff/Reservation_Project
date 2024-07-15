package com.zero.reservation.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity(name = "admin")
@NoArgsConstructor
public class Admin {

    @Id
    private String userId;

    private String storeName;
    private String storeAddress;
    private String storeInfo;
    private LocalDate registrationDate;
}
