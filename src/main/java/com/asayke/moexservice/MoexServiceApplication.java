package com.asayke.moexservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MoexServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoexServiceApplication.class, args);
	}

}