package com.fiap.techchallenge.morador;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(info = @Info(title = "API - Morador", version = "0.0.1"))
public class MoradorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoradorApplication.class, args);
	}

}
