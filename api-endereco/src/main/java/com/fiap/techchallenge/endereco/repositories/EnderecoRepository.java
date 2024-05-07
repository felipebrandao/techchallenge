package com.fiap.techchallenge.endereco.repositories;

import com.fiap.techchallenge.endereco.entities.Endereco;
import com.fiap.techchallenge.endereco.enums.EstadoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    @Query("SELECT e FROM Endereco e " +
            "WHERE (:rua IS NULL OR LOWER(e.rua) LIKE CONCAT('%', LOWER(:rua), '%')) " +
            "AND (:bairro IS NULL OR LOWER(e.bairro) LIKE CONCAT('%', LOWER(:bairro), '%')) " +
            "AND (:cidade IS NULL OR LOWER(e.cidade) LIKE CONCAT('%', LOWER(:cidade), '%'))")
    List<Endereco> buscarEnderecosPorFiltros(@Param("rua") String rua,
                                             @Param("bairro") String bairro,
                                             @Param("cidade") String cidade);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
            "FROM Endereco e " +
            "WHERE e.rua = :rua " +
            "AND e.numero = :numero " +
            "AND e.bairro = :bairro " +
            "AND e.cidade = :cidade " +
            "AND e.estado = :estado")
    boolean isExisteEnderecoCadastrado(
            String rua,
            Integer numero,
            String bairro,
            String cidade,
            EstadoEnum estado
    );
}
