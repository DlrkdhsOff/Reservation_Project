package com.zero.reservation.repository;

import com.zero.reservation.model.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Store, Long> {


}
