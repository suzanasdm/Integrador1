package br.unipar.devbackend.projetointegrador.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificacoes")
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String mensagem;

    private String tipo;

    private LocalDateTime dataEnvio;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Métodos: enviar(), armazenarHistorico()
}
