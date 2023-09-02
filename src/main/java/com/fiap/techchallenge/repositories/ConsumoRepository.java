package com.fiap.techchallenge.repositories;

import com.fiap.techchallenge.entities.Consumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ConsumoRepository extends JpaRepository<Consumo, Long> {

    @Query("SELECT c FROM Consumo c WHERE " +
            "(:dataInicio IS NULL OR c.dataConsumo >= :dataInicio) AND " +
            "(:dataFim IS NULL OR c.dataConsumo <= :dataFim) AND " +
            "(:idPessoa IS NULL OR c.morador.pessoa.id = :idPessoa) AND " +
            "(:idEndereco IS NULL OR c.morador.endereco.id = :idEndereco) AND " +
            "(:idEletrodomestico IS NULL OR c.eletrodomestico.id = :idEletrodomestico)")
    List<Consumo> pesquisarConsumos(
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("idPessoa") Long idPessoa,
            @Param("idEndereco") Long idEndereco,
            @Param("idEletrodomestico") Long idEletrodomestico);

}