package com.fiap.techchallenge.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "moradores")
@NoArgsConstructor
@Getter
@Setter
public class Morador {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_endereco")
    private Long idEndereco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco", referencedColumnName = "id", insertable = false, updatable = false)
    private Endereco endereco;

    @Column(name = "id_pessoa")
    private Long idPessoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id", insertable = false, updatable = false)
    private Pessoa pessoa;

    @Column(name = "parentesco")
    private String parentesco;

    public Morador(Long idEndereco, Long idPessoa, String parentesco) {
        this.idEndereco = idEndereco;
        this.idPessoa = idPessoa;
        this.parentesco = parentesco;
    }
}

