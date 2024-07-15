package com.zero.reservation.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity(name = "admin")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {

    @Id
    private String userId;

    private String storeName;
    private String storeAddress;
    private String storeInfo;
    private LocalDate registrationDate;
}
