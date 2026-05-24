package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.dto.FluxoCaixaDTO;
import br.unipar.devbackend.projetointegrador.dto.MovimentacaoDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class FluxoCaixaService {

    private final MovimentacaoService movimentacaoService;

    public FluxoCaixaService(MovimentacaoService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }

    public FluxoCaixaDTO gerarFluxoCaixa(
            Long usuarioId,
            LocalDate inicio,
            LocalDate fim
    ) {
        validarPeriodo(inicio, fim);

        List<MovimentacaoDTO> movimentacoesDoUsuario =
                movimentacaoService.listarPorUsuario(usuarioId);

        List<MovimentacaoDTO> movimentacoesFiltradas =
                movimentacoesDoUsuario.stream()
                        .filter(mov -> {
                            LocalDate dataMovimentacao = mov.getData().toLocalDate();

                            return !dataMovimentacao.isBefore(inicio)
                                    && !dataMovimentacao.isAfter(fim);
                        })
                        .toList();

        Double entradas = movimentacoesFiltradas.stream()
                .filter(mov -> "RECEITA".equals(mov.getTipo()))
                .mapToDouble(MovimentacaoDTO::getValor)
                .sum();

        Double saidas = movimentacoesFiltradas.stream()
                .filter(mov -> "DESPESA".equals(mov.getTipo()))
                .mapToDouble(MovimentacaoDTO::getValor)
                .sum();

        Double saldo = entradas - saidas;

        return new FluxoCaixaDTO(
                inicio,
                fim,
                entradas,
                saidas,
                saldo,
                movimentacoesFiltradas.size(),
                movimentacoesFiltradas
        );
    }

    private void validarPeriodo(LocalDate inicio, LocalDate fim) {

        if (inicio == null || fim == null) {
            throw new RuntimeException("Informe o período inicial e final.");
        }

        if (inicio.isAfter(fim)) {
            throw new RuntimeException("A data inicial não pode ser maior que a data final.");
        }

        if (fim.isAfter(LocalDate.now())) {
            throw new RuntimeException("O período selecionado não pode ser maior que a data atual.");
        }

        long dias = ChronoUnit.DAYS.between(inicio, fim);

        if (dias > 31) {
            throw new RuntimeException("O período selecionado não pode exceder 1 mês.");
        }
    }
}