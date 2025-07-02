package com.example.ecommerceinventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EcommerceInventoryManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceInventoryManagementApplication.class, args);
	}

}
