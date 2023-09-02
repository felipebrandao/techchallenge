package com.fiap.techchallenge.repositories;

import com.fiap.techchallenge.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    boolean existsByCpf(String cpf);

    @Query("SELECT p FROM Pessoa p WHERE p.id = :id AND p.cpf = :cpf")
    Pessoa findByIdAndCPF(@Param("id") Long id, @Param("cpf") String cpf);

    @Query("SELECT p FROM Pessoa p " +
            "WHERE (:nome IS NULL OR p.nome LIKE %:nome%) " +
            "And (:cpf IS NULL OR p.cpf = :cpf)")
    List<Pessoa> findByNomeAndCpf(@Param("nome") String nome, @Param("cpf") String cpf);
}
