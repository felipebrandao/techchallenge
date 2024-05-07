package com.fiap.techchallenge.morador.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "moradores")
@NoArgsConstructor
@Data
public class Morador {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_endereco")
    private Long idEndereco;

    @Column(name = "id_pessoa")
    private Long idPessoa;

    @Column(name = "parentesco")
    private String parentesco;

    public Morador(Long idEndereco, Long idPessoa, String parentesco) {
        this.idEndereco = idEndereco;
        this.idPessoa = idPessoa;
        this.parentesco = parentesco;
    }
}

