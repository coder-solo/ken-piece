package com.ken;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class PieceApplication {

	public static void main(String args[]) {
		SpringApplication.run(PieceApplication.class, args);
	}
}
