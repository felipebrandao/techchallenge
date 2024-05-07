package com.fiap.techchallenge.eletrodomestico;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API - Eletrodomestico", version = "0.0.1"))
public class EletrodomesticoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EletrodomesticoApplication.class, args);
	}

}
