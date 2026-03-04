package br.unipar.devbackend.projetointegrador.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "relatorios")
public class Relatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;

    @Column(nullable = false)
    private LocalDate periodoInicio;

    @Column(nullable = false)
    private LocalDate periodoFim;

    private String formato;

    // Métodos: gerarRelatorio()
}