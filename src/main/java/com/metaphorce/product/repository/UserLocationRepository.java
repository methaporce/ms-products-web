package com.metaphorce.product.repository;

import com.metaphorce.commonslib.entities.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {
}
