package com.fiap.techchallenge.repositories;

import com.fiap.techchallenge.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    @Query("SELECT e FROM Endereco e WHERE (:rua IS NULL OR e.rua LIKE %:rua%) " +
            "AND (:bairro IS NULL OR e.bairro LIKE %:bairro%) " +
            "AND (:cidade IS NULL OR e.cidade LIKE %:cidade%)")
    List<Endereco> buscarEnderecosPorFiltros(String rua, String bairro, String cidade);
}
