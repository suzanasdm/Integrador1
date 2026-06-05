package br.unipar.devbackend.projetointegrador.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "codigos_recuperacao")
public class CodigoRecuperacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String codigo;

    // Atenção aqui: o JPA transforma CamelCase em snake_case (data_expiracao) no banco automaticamente
    @Column(name = "data_expiracao", nullable = false)
    private LocalDateTime dataExpiracao;

    // 1. CONSTRUTOR PADRÃO (OBRIGATÓRIO para o Hibernate funcionar)
    public CodigoRecuperacao() {}

    // 2. CONSTRUTOR COMPLETO (O que você usa no seu AutenticacaoService)
    public CodigoRecuperacao(String email, String codigo, LocalDateTime dataExpiracao) {
        this.email = email;
        this.codigo = codigo;
        this.dataExpiracao = dataExpiracao;
    }

    // 3. GETTERS E SETTERS (Verifique se os nomes estão EXATAMENTE assim)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(LocalDateTime dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }
}