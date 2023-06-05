package com.fiap.techchallenge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.techchallenge.entities.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    boolean existsByCpf(String cpf);
}
