package br.unipar.devbackend.projetointegrador.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    private CategoriaEnum tipo;



    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;



  }
