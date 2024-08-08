package com.metaphorce.product.controller;


import com.metaphorce.commonslib.entities.Card;
import com.metaphorce.commonslib.entities.UserLocation;
import com.metaphorce.commonslib.entities.Users;
import com.metaphorce.product.repository.CardRepository;
import com.metaphorce.product.repository.CartRepository;
import com.metaphorce.product.repository.UserLocationRepository;
import com.metaphorce.product.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLocationRepository userLocationRepository;

    @Autowired
    private CardRepository cartRepository;

    @GetMapping("/getUserById")
    public ResponseEntity<Users> getUserById(@RequestParam Long id) {

        Optional<Users> user = userRepository.findById(id);

        return  ResponseEntity.ok(user.get());
    }

    @GetMapping("/getUserLocation")
    public ResponseEntity<List<UserLocation>> getUserLocation(@RequestParam Long id) {

        Optional<Users> user = userRepository.findById(id);

        Optional<List<UserLocation>> userLocation = userLocationRepository.findByUserId(id);

        if (userLocation.isEmpty()) {
            return  ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userLocation.get());

    }

    @GetMapping("/getCardsByUserId")
    public ResponseEntity<List<Card>> getCardsByUserId(@RequestParam Long id) {

        Optional<Users> user = userRepository.findById(id);

        Optional<List<Card>> cards = cartRepository.findByUserId(id);

        if (cards.isEmpty()) {
            return  ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cards.get());
    }
}
