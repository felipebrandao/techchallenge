package com.fiap.techchallenge.morador.repositories;

import com.fiap.techchallenge.morador.entities.Morador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MoradorRepository extends JpaRepository<Morador, Long> {
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END " +
            "FROM Morador m " +
            "WHERE m.idEndereco = :idEndereco " +
            "AND m.idPessoa = :idPessoa")
    boolean isExistePessoaCadastradaNoEndereco(@Param("idEndereco") Long idEndereco,
                                               @Param("idPessoa") Long idPessoa);

    @Query("SELECT m FROM Morador m WHERE "
            + "(:idPessoa IS NULL OR m.idPessoa = :idPessoa) AND "
            + "(:idEndereco IS NULL OR m.idEndereco = :idEndereco)")
    List<Morador> pesquisarMoradores(
            @Param("idPessoa") Long idPessoa,
            @Param("idEndereco") Long idEndereco);
}
