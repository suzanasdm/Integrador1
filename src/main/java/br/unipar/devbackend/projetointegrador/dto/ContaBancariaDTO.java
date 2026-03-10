package br.unipar.devbackend.projetointegrador.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class ContaBancariaDTO {
    @NotBlank(message = "O banco é obrigatório")
    private String banco;

    @NotBlank(message = "A agência é obrigatória")
    @Size(min = 3, max = 5, message = "A agência deve ter entre 3 e 5 caracteres") // RN-03
    private String agencia;

    @NotBlank(message = "O número da conta é obrigatório")
    private String numeroConta;

    @NotNull(message = "O saldo inicial é obrigatório")
    @Min(value = 0, message = "O saldo inicial deve ser maior ou igual a zero") // RN-02
    private Double saldo;

    @NotNull(message = "O ID do usuário é obrigatório")
    private Long usuarioId;
}
