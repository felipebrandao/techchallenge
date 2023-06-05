package com.fiap.techchallenge.entities;

import java.time.LocalDate;

import com.fiap.techchallenge.enums.SexoEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Pessoa {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String nome;
    @Column
    private LocalDate dataDeNascimento;
    @Column(name = "sexo")
    @Enumerated(EnumType.STRING)
    private SexoEnum sexo;
    @Column(name = "cpf")
    private String cpf;

    public Pessoa(String nome, LocalDate dataDeNascimento, SexoEnum sexo, String cpf) {
        this.nome = nome;
        this.dataDeNascimento = dataDeNascimento;
        this.sexo = sexo;
        this.cpf = cpf;
    }
}
