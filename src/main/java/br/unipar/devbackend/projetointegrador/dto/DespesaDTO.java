package br.unipar.devbackend.projetointegrador.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DespesaDTO {

    private String descricao;
    private Double valor;
    private LocalDate data;
    private Long usuarioId;
    private Long categoriaId;
    private long  ContaId;

    // O construtor padrão é necessário para o Jackson (JSON)
    public DespesaDTO() {}

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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
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

    public long getContaId() {
        return ContaId;
    }

    public void setContaId(long contaId) {
        ContaId = contaId;
    }

    public DespesaDTO(String descricao, Double valor, LocalDate data, Long usuarioId, Long categoriaId, long contaId) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.usuarioId = usuarioId;
        this.categoriaId = categoriaId;
        ContaId = contaId;


    }
}