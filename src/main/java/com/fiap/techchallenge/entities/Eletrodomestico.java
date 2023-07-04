package com.fiap.techchallenge.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Eletrodomestico {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "potencia")
    private Double potencia;

    public Eletrodomestico(String nome, String modelo, Double potencia) {
        this.nome = nome;
        this.modelo = modelo;
        this.potencia = potencia;
    }
}
