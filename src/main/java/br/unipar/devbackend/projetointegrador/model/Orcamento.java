package br.unipar.devbackend.projetointegrador.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "orcamentos")
@Data
public class Orcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valorLimite;
    private Double valorGasto; // Calculado dinamicamente ou atualizado via scheduler/trigger
    private String mesAno; // Ex: "2026-05" (para controlar orçamentos mensais)

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    // Métodos: criarOrcamento(), editarOrcamento()
}