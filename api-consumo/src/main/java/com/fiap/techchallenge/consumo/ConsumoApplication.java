package com.fiap.techchallenge.consumo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(info = @Info(title = "API - Consumo", version = "0.0.1"))
public class ConsumoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumoApplication.class, args);
	}

}
