package com.comunetmax.ms_empresas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients; // <--- ESTE IMPORT ES VITAL

@SpringBootApplication
@EnableFeignClients
public class MsEmpresasApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsEmpresasApplication.class, args);
	}
}