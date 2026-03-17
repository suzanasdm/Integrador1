package br.unipar.devbackend.projetointegrador.dto;

import br.unipar.devbackend.projetointegrador.model.CategoriaEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoriaDTO {
    @NotBlank(message = "A descrição da categoria é obrigatória")
    private String descricao;

    @NotNull(message = "O tipo (RECEITA/DESPESA) é obrigatório")
    private CategoriaEnum tipo;

    @NotNull(message = "O ID do utilizador é obrigatório")
    private Long usuarioId;
}
