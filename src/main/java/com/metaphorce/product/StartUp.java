package com.metaphorce.product;

import com.metaphorce.commonslib.entities.Category;
import com.metaphorce.commonslib.entities.PaymentMethod;
import com.metaphorce.commonslib.entities.Product;
import com.metaphorce.commonslib.entities.Users;
import com.metaphorce.product.repository.CategoryRepository;
import com.metaphorce.product.repository.PaymentMethodRepository;
import com.metaphorce.product.repository.ProductRepository;
import com.metaphorce.product.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
public class StartUp {

    @Bean
    ApplicationRunner initDatabase(
            PaymentMethodRepository paymentMethodRepository,
            UserRepository userRepository,
            CategoryRepository categoryRepository,
            ProductRepository productRepository
    ) {
        return args -> {

            paymentMethodRepository.save(new PaymentMethod(null, PaymentMethod.PaymentMethodEnum.CREDIT_CARD));
            paymentMethodRepository.save(new PaymentMethod(null, PaymentMethod.PaymentMethodEnum.DEBIT_CARD));
            paymentMethodRepository.save(new PaymentMethod(null, PaymentMethod.PaymentMethodEnum.TRANSFER));
            paymentMethodRepository.save(new PaymentMethod(null, PaymentMethod.PaymentMethodEnum.CASH));
            paymentMethodRepository.save(new PaymentMethod(null, PaymentMethod.PaymentMethodEnum.OTHER));

            userRepository.save(new Users(
                    1L,
                    "emontalvo",
                    "Erick",
                    "Montalvo",
                    "p@ssw0rd",
                    "9721048530",
                    "admin@email.com",
                    "ADMIN",
                    true,
                    false,
                    LocalDateTime.now(),
                    LocalDateTime.now()));

            userRepository.save(new Users(
                    12L,
                    "testeruser",
                    "Tester",
                    "User",
                    "testerpassword",
                    "0000000001",
                    "tester@email.com",
                    "TESTER",
                    true,
                    false,
                    LocalDateTime.now(),
                    LocalDateTime.now()));


            Category categoryTecnologia = categoryRepository.save(new Category(
                    1L,
                    "Tecnologia",
                    null,
                    true,
                    false,
                    LocalDateTime.now(),
                    LocalDateTime.now()));


            categoryRepository.save(new Category(
                    2L,
                    "Hogar",
                    null,
                    true,
                    false,
                    LocalDateTime.now(),
                    LocalDateTime.now()));

            categoryRepository.save(new Category(
                    3L,
                    "Ropa",
                    null,
                    true,
                    false,
                    LocalDateTime.now(),
                    LocalDateTime.now()));

            categoryRepository.save(new Category(
                    4L,
                    "Calzado",
                    null,
                    true,
                    false,
                    LocalDateTime.now(),
                    LocalDateTime.now()));


            productRepository.save(new Product(
                    1L,
                    categoryTecnologia,
                    "iPhone 14 Pro Max",
                    new BigDecimal(29000),
                    150,
                    "https://i.pinimg.com/564x/79/e6/1a/79e61aae36bf6125f87c7e3ec3e7dfbb.jpg",
                    "256 GB, 6 GB RAM, iOS 16, Color Titanio Natural, Camara frontal: 64 MP, Camara trasera: 48 MP",
                    true,
                    false,
                    LocalDateTime.now(),
                    LocalDateTime.now()));

            productRepository.save(new Product(
                    2L,
                    categoryTecnologia,
                    "MacBook Pro 14",
                    new BigDecimal(35000),
                    200,
                    "https://cdsassets.apple.com/live/SZLF0YNV/images/sp/111901_mbp16-gray.png",
                    "16 GB, 8 GB RAM, macOS 13.3, Color Gris",
                    true,
                    false,
                    LocalDateTime.now(),
                    LocalDateTime.now()));

            productRepository.save(new Product(
                    3L,
                    categoryTecnologia,
                    "Samsung Galaxy S24 Ultra",
                    new BigDecimal(25000),
                    100,
                    "https://images.samsung.com/is/image/samsung/p6pim/mx/2401/gallery/mx-galaxy-s24-s928-sm-s928bztvltm-539300719?$650_519_PNG$",
                    "512 GB, 8 GB RAM, Android 16, Color Violeta",
                    true,
                    false,
                    LocalDateTime.now(),
                    LocalDateTime.now()));

        };
    }

}
