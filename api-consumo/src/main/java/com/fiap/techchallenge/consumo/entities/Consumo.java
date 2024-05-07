package com.fiap.techchallenge.consumo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "consumo")
@NoArgsConstructor
@Getter
@Setter
public class Consumo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConsumo;

    @Column(name = "id_morador")
    private Long idMorador;

    @Column(name = "id_eletrodomestico")
    private Long idEletrodomestico;

    @Column(name = "data_consumo")
    private LocalDate dataConsumo;

    @Column(name = "tempo_consumo")
    private Double tempoConsumo;

    public Consumo(Long idMorador, Long idEletrodomestico, LocalDate dataConsumo, Double tempoConsumo) {
        this.idMorador = idMorador;
        this.idEletrodomestico = idEletrodomestico;
        this.dataConsumo = dataConsumo;
        this.tempoConsumo = tempoConsumo;
    }
}
