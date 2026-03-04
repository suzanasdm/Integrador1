package br.unipar.devbackend.projetointegrador.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "metas")
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private Double valor;

    private LocalDate dataLimite;

    private String prioridade;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}