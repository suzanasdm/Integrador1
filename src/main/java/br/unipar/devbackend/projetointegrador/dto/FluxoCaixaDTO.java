package br.unipar.devbackend.projetointegrador.dto;

import java.time.LocalDate;
import java.util.List;

public class FluxoCaixaDTO {

    private LocalDate periodoInicio;
    private LocalDate periodoFim;
    private Double entradas;
    private Double saidas;
    private Double saldo;
    private Integer quantidadeMovimentacoes;
    private List<MovimentacaoDTO> movimentacoes;

    public FluxoCaixaDTO() {
    }

    public FluxoCaixaDTO(
            LocalDate periodoInicio,
            LocalDate periodoFim,
            Double entradas,
            Double saidas,
            Double saldo,
            Integer quantidadeMovimentacoes,
            List<MovimentacaoDTO> movimentacoes
    ) {
        this.periodoInicio = periodoInicio;
        this.periodoFim = periodoFim;
        this.entradas = entradas;
        this.saidas = saidas;
        this.saldo = saldo;
        this.quantidadeMovimentacoes = quantidadeMovimentacoes;
        this.movimentacoes = movimentacoes;
    }

    public LocalDate getPeriodoInicio() {
        return periodoInicio;
    }

    public void setPeriodoInicio(LocalDate periodoInicio) {
        this.periodoInicio = periodoInicio;
    }

    public LocalDate getPeriodoFim() {
        return periodoFim;
    }

    public void setPeriodoFim(LocalDate periodoFim) {
        this.periodoFim = periodoFim;
    }

    public Double getEntradas() {
        return entradas;
    }

    public void setEntradas(Double entradas) {
        this.entradas = entradas;
    }

    public Double getSaidas() {
        return saidas;
    }

    public void setSaidas(Double saidas) {
        this.saidas = saidas;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Integer getQuantidadeMovimentacoes() {
        return quantidadeMovimentacoes;
    }

    public void setQuantidadeMovimentacoes(Integer quantidadeMovimentacoes) {
        this.quantidadeMovimentacoes = quantidadeMovimentacoes;
    }

    public List<MovimentacaoDTO> getMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes(List<MovimentacaoDTO> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }
}