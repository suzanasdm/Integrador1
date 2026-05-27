package br.unipar.devbackend.projetointegrador.dto;

import br.unipar.devbackend.projetointegrador.model.CategoriaEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TransacaoUpdateDTO {

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotNull(message = "O tipo é obrigatório")
    private CategoriaEnum tipo;

    private Long categoriaId;

    private Long usuarioId;

    public TransacaoUpdateDTO() {
    }

    public String getDescricao() {
        return descricao;
    }

    public CategoriaEnum getTipo() {
        return tipo;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setTipo(CategoriaEnum tipo) {
        this.tipo = tipo;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}