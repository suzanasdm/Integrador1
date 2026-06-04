package br.unipar.devbackend.projetointegrador.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OrcamentoDTO {

    @NotNull(message = "O valor limite é obrigatório")
    @Positive(message = "O valor limite deve ser maior que zero")
    private Double valorLimite;

    @NotNull(message = "A categoria é obrigatória")
    private Long categoriaId;

    @NotNull(message = "O usuário é obrigatório")
    private Long usuarioId;

    @NotBlank(message = "O mês de vigência é obrigatório")
    private String mesAno;

    public Double getValorLimite() {
        return valorLimite;
    }

    public void setValorLimite(Double valorLimite) {
        this.valorLimite = valorLimite;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getMesAno() {
        return mesAno;
    }

    public void setMesAno(String mesAno) {
        this.mesAno = mesAno;
    }
}