package com.fiap.techchallenge.morador.feign;

import com.fiap.techchallenge.morador.feign.dtos.PessoaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "api-pessoa", url = "${api-pessoa.url}")
public interface PessoaFeignClient {

    @GetMapping("/api/pessoa/{id}")
    PessoaDTO getPessoaById(Long id);
}
