package com.fiap.techchallenge.morador.feign;

import com.fiap.techchallenge.morador.feign.dtos.EnderecoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "api-endereco", url = "${api-endereco.url}")
public interface EnderecoFeignClient {

    @GetMapping("/api/endereco/{id}")
    EnderecoDTO getEnderecoById(Long id);
}
