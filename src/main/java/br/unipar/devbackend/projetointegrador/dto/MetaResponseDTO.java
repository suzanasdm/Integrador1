package br.unipar.devbackend.projetointegrador.dto;

import br.unipar.devbackend.projetointegrador.model.Meta;

import java.time.LocalDate;

public class MetaResponseDTO {

    private Long id;
    private String descricao;
    private Double valorObjetivo;
    private Double valorAtual;
    private Double percentual;
    private LocalDate prazo;
    private String prioridade;
    private String status;
    private Long categoriaId;
    private String categoriaNome;

    public MetaResponseDTO(Meta meta) {
        this.id = meta.getId();
        this.descricao = meta.getDescricao();
        this.valorObjetivo = meta.getValorObjetivo();
        this.valorAtual = meta.getValorAtual();
        this.prazo = meta.getPrazo();
        this.prioridade = meta.getPrioridade();
        this.status = meta.getStatus();

        if (meta.getValorObjetivo() != null && meta.getValorObjetivo() > 0) {
            this.percentual = (meta.getValorAtual() / meta.getValorObjetivo()) * 100;
        } else {
            this.percentual = 0.0;
        }

        if (meta.getCategoria() != null) {
            this.categoriaId = meta.getCategoria().getId();
            this.categoriaNome = meta.getCategoria().getNome();
        }
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getValorObjetivo() {
        return valorObjetivo;
    }

    public Double getValorAtual() {
        return valorAtual;
    }

    public Double getPercentual() {
        return percentual;
    }

    public LocalDate getPrazo() {
        return prazo;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public String getStatus() {
        return status;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }
}