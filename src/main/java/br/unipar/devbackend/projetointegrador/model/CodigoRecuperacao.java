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


    @Column(name = "data_expiracao", nullable = false)
    private LocalDateTime dataExpiracao;


    public CodigoRecuperacao() {}


    public CodigoRecuperacao(String email, String codigo, LocalDateTime dataExpiracao) {
        this.email = email;
        this.codigo = codigo;
        this.dataExpiracao = dataExpiracao;
    }

   
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