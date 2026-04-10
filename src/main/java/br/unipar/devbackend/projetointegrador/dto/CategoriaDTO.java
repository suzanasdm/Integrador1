package br.unipar.devbackend.projetointegrador.dto;

import br.unipar.devbackend.projetointegrador.model.CategoriaEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoriaDTO {

    private Long id;
    @NotBlank(message = "O nome da categoria é obrigatória")
    private String nome;

    @NotNull(message = "O tipo (RECEITA/DESPESA) é obrigatório")
    private CategoriaEnum tipo;

    @NotNull(message = "O ID do utilizador é obrigatório")
    private Long usuarioId;

    public CategoriaDTO() {

    }

    public CategoriaDTO(String nome, CategoriaEnum tipo, Long usuarioId) {
        this.nome = nome;
        this.tipo = tipo;
        this.usuarioId = usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CategoriaEnum getTipo() {
        return tipo;
    }

    public void setTipo(CategoriaEnum tipo) {
        this.tipo = tipo;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
