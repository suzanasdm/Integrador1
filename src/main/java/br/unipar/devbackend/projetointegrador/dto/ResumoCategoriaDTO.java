package br.unipar.devbackend.projetointegrador.dto;

public class ResumoCategoriaDTO {

    private String categoria;
    private Double valor;
    private Double percentual;

    public ResumoCategoriaDTO() {
    }

    public ResumoCategoriaDTO(String categoria, Double valor, Double percentual) {
        this.categoria = categoria;
        this.valor = valor;
        this.percentual = percentual;
    }

    public String getCategoria() {
        return categoria;
    }

    public Double getValor() {
        return valor;
    }

    public Double getPercentual() {
        return percentual;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setPercentual(Double percentual) {
        this.percentual = percentual;
    }
}