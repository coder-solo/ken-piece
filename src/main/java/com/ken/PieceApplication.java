package com.ken;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
//@SpringBootApplication
public class PieceApplication {

	public static void main(String args[]) {
		SpringApplication.run(PieceApplication.class, args);
	}
}
