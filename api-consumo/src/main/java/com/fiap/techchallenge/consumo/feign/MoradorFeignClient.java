package com.fiap.techchallenge.consumo.feign;

import com.fiap.techchallenge.consumo.feign.dtos.MoradorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "api-morador", url = "${api-morador.url}")
public interface MoradorFeignClient {

    @GetMapping("/api/morador/{id}")
    MoradorDTO getMoradorById(Long id);
}
