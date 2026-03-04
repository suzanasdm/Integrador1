package br.unipar.devbackend.projetointegrador.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ContaBancaria {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    @Column(nullable = false)
    private String banco;

    private String agencia;

    @Column(name = "numero_conta")
    private String numeroConta;

    private Double saldo;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
