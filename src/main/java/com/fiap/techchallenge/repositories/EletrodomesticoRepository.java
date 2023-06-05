package com.fiap.techchallenge.repositories;

import com.fiap.techchallenge.entities.Eletrodomestico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EletrodomesticoRepository extends JpaRepository<Eletrodomestico, Long> {
}
