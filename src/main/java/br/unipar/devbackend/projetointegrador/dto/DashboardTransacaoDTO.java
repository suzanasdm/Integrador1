package br.unipar.devbackend.projetointegrador.dto;

import java.time.LocalDate;

public class DashboardTransacaoDTO {

    private LocalDate data;
    private String descricao;
    private String categoria;
    private String tipo;
    private Double valor;

    public DashboardTransacaoDTO() {
    }

    public DashboardTransacaoDTO(LocalDate data, String descricao, String categoria, String tipo, Double valor) {
        this.data = data;
        this.descricao = descricao;
        this.categoria = categoria;
        this.tipo = tipo;
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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
}