package com.fiap.techchallenge.repositories;

import com.fiap.techchallenge.entities.Eletrodomestico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EletrodomesticoRepository extends JpaRepository<Eletrodomestico, Long> {

    @Query("SELECT e FROM Eletrodomestico e WHERE " +
            "(:nome IS NULL OR e.nome = :nome) AND " +
            "(:modelo IS NULL OR e.modelo = :modelo) AND " +
            "(:potencia IS NULL OR e.potencia = :potencia)")
    List<Eletrodomestico> pesquisarEletrodomesticos(
            @Param("nome") String nome,
            @Param("modelo") String modelo,
            @Param("potencia") Double potencia);

}
