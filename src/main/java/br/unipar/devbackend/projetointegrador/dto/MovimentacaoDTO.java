package br.unipar.devbackend.projetointegrador.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MovimentacaoDTO {

    private Long id;
    private LocalDateTime data;
    private String descricao;
    private String tipo;
    private Double valor;
    private String categoria;;
    private String conta;
    private String origem;

    public MovimentacaoDTO() {
    }

    public MovimentacaoDTO(Long id, LocalDateTime data, String descricao, String tipo, Double valor, String categoria, String conta, String origem) {
        this.id = id;
        this.data = data;
        this.descricao = descricao;
        this.tipo = tipo;
        this.valor = valor;
        this.categoria = categoria;
        this.conta = conta;
        this.origem = origem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }
}
