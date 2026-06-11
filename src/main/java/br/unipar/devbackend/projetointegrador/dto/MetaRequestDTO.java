package br.unipar.devbackend.projetointegrador.dto;

import java.time.LocalDate;

public class MetaRequestDTO {

    private String descricao;
    private Double valorObjetivo;
    private Double valorAtual;
    private LocalDate prazo;
    private String prioridade;
    private Long usuarioId;
    private Long categoriaId;

    public String getDescricao() {
        return descricao;
    }

    public Double getValorObjetivo() {
        return valorObjetivo;
    }

    public Double getValorAtual() {
        return valorAtual;
    }

    public LocalDate getPrazo() {
        return prazo;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValorObjetivo(Double valorObjetivo) {
        this.valorObjetivo = valorObjetivo;
    }

    public void setValorAtual(Double valorAtual) {
        this.valorAtual = valorAtual;
    }

    public void setPrazo(LocalDate prazo) {
        this.prazo = prazo;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }
}