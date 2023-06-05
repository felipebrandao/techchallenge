package com.fiap.techchallenge.entities;

import com.fiap.techchallenge.enums.EstadoEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Endereco {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "rua")
    private String rua;
    @Column(name = "numero")
    private Integer numero;
    @Column(name = "bairro")
    private String bairro;
    @Column(name = "cidade")
    private String cidade;
    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private EstadoEnum estado;

    public Endereco(String rua, Integer numero, String bairro, String cidade, EstadoEnum estado) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }
}
