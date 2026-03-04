package br.unipar.devbackend.projetointegrador.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "orcamentos")
public class Orcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Column(nullable = false)
    private LocalDate periodoInicio;

    @Column(nullable = false)
    private LocalDate periodoFim;

    @Column(nullable = false)
    private Double valorLimite;

    // Métodos: criarOrcamento(), editarOrcamento()
}