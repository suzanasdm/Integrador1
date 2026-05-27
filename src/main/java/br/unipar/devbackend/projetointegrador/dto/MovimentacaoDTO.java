package br.unipar.devbackend.projetointegrador.dto;

import java.time.LocalDateTime;

public class MovimentacaoDTO {

    private Long id;
    private LocalDateTime data;
    private String descricao;
    private String tipo;
    private Double valor;
    private String categoria;
    private Long categoriaId;
    private String conta;
    private Long contaId;
    private String origem;

    public MovimentacaoDTO() {
    }

    public MovimentacaoDTO(
            Long id,
            LocalDateTime data,
            String descricao,
            String tipo,
            Double valor,
            String categoria,
            Long categoriaId,
            String conta,
            Long contaId,
            String origem
    ) {
        this.id = id;
        this.data = data;
        this.descricao = descricao;
        this.tipo = tipo;
        this.valor = valor;
        this.categoria = categoria;
        this.categoriaId = categoriaId;
        this.conta = conta;
        this.contaId = contaId;
        this.origem = origem;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public Double getValor() {
        return valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public String getConta() {
        return conta;
    }

    public Long getContaId() {
        return contaId;
    }

    public String getOrigem() {
        return origem;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public void setContaId(Long contaId) {
        this.contaId = contaId;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }
}