package com.fiap.techchallenge.repositories;

import com.fiap.techchallenge.entities.Eletrodomestico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EletrodomesticoRepository extends JpaRepository<Eletrodomestico, Long> {

    List<Eletrodomestico> findByNomeOrModeloOrPotencia(String nome, String modelo, Double potencia);

}
