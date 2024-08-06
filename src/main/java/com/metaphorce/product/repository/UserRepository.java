package com.metaphorce.product.repository;

import com.metaphorce.commonslib.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

}
