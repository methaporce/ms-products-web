package com.metaphorce.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.metaphorce.commonslib")
public class MsProductWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsProductWebApplication.class, args);
	}

}
