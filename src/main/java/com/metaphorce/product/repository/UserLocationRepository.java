package com.metaphorce.product.repository;

import com.metaphorce.commonslib.entities.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {

    Optional<List<UserLocation>> findByUserId(Long userId);
}
