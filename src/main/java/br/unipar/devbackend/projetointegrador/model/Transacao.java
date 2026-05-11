package br.unipar.devbackend.projetointegrador.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @Column(nullable = false)
    private Double valor;

    private LocalDateTime data;

    // ID único vindo do OFX
    @Column(unique = true)
    private String fitId;

    // Entrada ou saída
    @Enumerated(EnumType.STRING)
    private CategoriaEnum tipo;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "conta_id")
    private ContaBancaria conta;

    public Transacao(Long id, String descricao, Double valor, LocalDateTime data, String fitId, CategoriaEnum tipo, Categoria categoria, ContaBancaria conta) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.fitId = fitId;
        this.tipo = tipo;
        this.categoria = categoria;
        this.conta = conta;
    }

    public Transacao() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getFitId() {
        return fitId;
    }

    public void setFitId(String fitId) {
        this.fitId = fitId;
    }

    public CategoriaEnum getTipo() {
        return tipo;
    }

    public void setTipo(CategoriaEnum tipo) {
        this.tipo = tipo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public ContaBancaria getConta() {
        return conta;
    }

    public void setConta(ContaBancaria conta) {
        this.conta = conta;
    }
}