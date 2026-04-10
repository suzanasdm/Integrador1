package br.unipar.devbackend.projetointegrador.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReceitaDTO {

    private String descricao;
    private Double valor;
    private Long usuarioId;
    private Long categoriaId;
    private LocalDate data;

    public ReceitaDTO() {
    }


    public ReceitaDTO(String descricao, Double valor, Long usuarioId, Long categoriaId, LocalDate data) {
        this.descricao = descricao;
        this.valor = valor;
        this.usuarioId = usuarioId;
        this.categoriaId = categoriaId;
        this.data = data;
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

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
