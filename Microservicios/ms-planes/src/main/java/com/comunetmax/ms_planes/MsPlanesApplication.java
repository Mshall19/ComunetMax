package com.comunetmax.ms_planes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsPlanesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPlanesApplication.class, args);
	}

}
