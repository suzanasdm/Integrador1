package br.unipar.devbackend.projetointegrador.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "conta_id") // Este campo "manda" na coluna
    private ContaBancaria conta;

    // Se você REALMENTE precisa manter o campo contaId separado:
    @Column(name = "conta_id", insertable = false, updatable = false)
    private Long contaId;



    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    private LocalDate data;

    // Relacionamento com Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Relacionamento com Categoria
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;



}