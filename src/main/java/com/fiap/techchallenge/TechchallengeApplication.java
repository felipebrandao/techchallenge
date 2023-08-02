package com.fiap.techchallenge;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Tech Challenge", version = "0.0.1"))
public class TechchallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechchallengeApplication.class, args);
	}

}
