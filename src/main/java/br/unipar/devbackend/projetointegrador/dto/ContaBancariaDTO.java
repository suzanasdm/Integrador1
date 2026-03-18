package br.unipar.devbackend.projetointegrador.dto;

import br.unipar.devbackend.projetointegrador.model.BancoEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class ContaBancariaDTO {
   
    private BancoEnum banco;

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


    public ContaBancariaDTO(BancoEnum banco, String agencia, String numeroConta, Double saldo, Long usuarioId) {
        this.banco = banco;
        this.agencia = agencia;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        this.usuarioId = usuarioId;
    }

    public BancoEnum getBanco() {
        return banco;
    }

    public void setBanco(BancoEnum banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
