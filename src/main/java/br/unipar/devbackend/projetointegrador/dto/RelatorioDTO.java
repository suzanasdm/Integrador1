package br.unipar.devbackend.projetointegrador.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class RelatorioDTO {

    private LocalDate periodoInicio;
    private LocalDate periodoFim;

    private Double receitas;
    private Double despesas;
    private Double saldo;

    private List<MovimentacaoDTO> movimentacoes;

    private List<Map<String, Object>> receitasPorCategoria;
    private List<Map<String, Object>> despesasPorCategoria;

    public RelatorioDTO() {
    }

    public RelatorioDTO(
            LocalDate periodoInicio,
            LocalDate periodoFim,
            Double receitas,
            Double despesas,
            Double saldo,
            List<MovimentacaoDTO> movimentacoes,
            List<Map<String, Object>> receitasPorCategoria,
            List<Map<String, Object>> despesasPorCategoria
    ) {
        this.periodoInicio = periodoInicio;
        this.periodoFim = periodoFim;
        this.receitas = receitas;
        this.despesas = despesas;
        this.saldo = saldo;
        this.movimentacoes = movimentacoes;
        this.receitasPorCategoria = receitasPorCategoria;
        this.despesasPorCategoria = despesasPorCategoria;
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

    public Double getReceitas() {
        return receitas;
    }

    public void setReceitas(Double receitas) {
        this.receitas = receitas;
    }

    public Double getDespesas() {
        return despesas;
    }

    public void setDespesas(Double despesas) {
        this.despesas = despesas;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public List<MovimentacaoDTO> getMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes(List<MovimentacaoDTO> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }

    public List<Map<String, Object>> getReceitasPorCategoria() {
        return receitasPorCategoria;
    }

    public void setReceitasPorCategoria(List<Map<String, Object>> receitasPorCategoria) {
        this.receitasPorCategoria = receitasPorCategoria;
    }

    public List<Map<String, Object>> getDespesasPorCategoria() {
        return despesasPorCategoria;
    }

    public void setDespesasPorCategoria(List<Map<String, Object>> despesasPorCategoria) {
        this.despesasPorCategoria = despesasPorCategoria;
    }
}