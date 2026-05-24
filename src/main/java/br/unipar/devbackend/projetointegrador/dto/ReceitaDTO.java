package br.unipar.devbackend.projetointegrador.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceitaDTO {

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser maior que zero")
    private Double valor;

    @NotNull(message = "O ID do usuário é obrigatório")
    private Long usuarioId;

    @NotNull(message = "O ID da categoria é obrigatório")
    private Long categoriaId;

    @NotNull(message = "O ID da conta bancária é obrigatório")
    private Long contaId;

    @NotNull(message = "A data é obrigatória")
    private LocalDate data;



}