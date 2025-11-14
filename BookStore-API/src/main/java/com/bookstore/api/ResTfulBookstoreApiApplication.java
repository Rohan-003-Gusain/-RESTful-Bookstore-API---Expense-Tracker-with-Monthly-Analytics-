package com.bookstore.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ResTfulBookstoreApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResTfulBookstoreApiApplication.class, args);
		System.out.println("RESTful Bookstore API is running...");
	}

}
