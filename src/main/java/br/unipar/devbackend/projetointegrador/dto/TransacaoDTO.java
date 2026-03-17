package br.unipar.devbackend.projetointegrador.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TransacaoDTO {
    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser maior que zero")
    private Double valor;

    @NotNull(message = "A data é obrigatória")
    private LocalDateTime data;

    @NotNull(message = "O ID da conta é obrigatório")
    private Long contaId;

    @NotNull(message = "O ID da categoria é obrigatório")
    private Long categoriaId;
}
