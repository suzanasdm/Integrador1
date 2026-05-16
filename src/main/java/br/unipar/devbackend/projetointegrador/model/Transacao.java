package br.unipar.devbackend.projetointegrador.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(
        name = "transacao",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"fit_id", "conta_id"})
        }
)
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @Column(nullable = false)
    private Double valor;

    private LocalDateTime data;

    @Column(name = "fit_id")
    private String fitId;

    @Enumerated(EnumType.STRING)
    private CategoriaEnum tipo;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "conta_id")
    private ContaBancaria conta;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Transacao() {
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getValor() {
        return valor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public String getFitId() {
        return fitId;
    }

    public CategoriaEnum getTipo() {
        return tipo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public ContaBancaria getConta() {
        return conta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public void setFitId(String fitId) {
        this.fitId = fitId;
    }

    public void setTipo(CategoriaEnum tipo) {
        this.tipo = tipo;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setConta(ContaBancaria conta) {
        this.conta = conta;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}