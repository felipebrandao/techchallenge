package com.fiap.techchallenge.entities;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_morador", referencedColumnName = "id", insertable = false, updatable = false)
    private Morador morador;

    @Column(name = "id_eletrodomestico")
    private Long idEletrodomestico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_eletrodomestico", referencedColumnName = "id", insertable = false, updatable = false)
    private Eletrodomestico eletrodomestico;

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
