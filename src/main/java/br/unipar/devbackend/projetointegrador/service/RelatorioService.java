package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.dto.MovimentacaoDTO;
import br.unipar.devbackend.projetointegrador.dto.RelatorioDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

    private final MovimentacaoService movimentacaoService;

    public RelatorioService(MovimentacaoService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }

    public RelatorioDTO gerarRelatorio(
            Long usuarioId,
            LocalDate inicio,
            LocalDate fim,
            String tipo,
            String origem,
            Long categoriaId
    ) {
        List<MovimentacaoDTO> movimentacoes =
                movimentacaoService.listarPorUsuario(usuarioId);

        List<MovimentacaoDTO> filtradas = movimentacoes.stream()
                .filter(mov -> filtrarPorPeriodo(mov, inicio, fim))
                .filter(mov -> filtrarPorTipo(mov, tipo))
                .filter(mov -> filtrarPorOrigem(mov, origem))
                .filter(mov -> filtrarPorCategoria(mov, categoriaId))
                .collect(Collectors.toList());

        Double receitas = filtradas.stream()
                .filter(mov -> "RECEITA".equalsIgnoreCase(mov.getTipo()))
                .mapToDouble(MovimentacaoDTO::getValor)
                .sum();

        Double despesas = filtradas.stream()
                .filter(mov -> "DESPESA".equalsIgnoreCase(mov.getTipo()))
                .mapToDouble(MovimentacaoDTO::getValor)
                .sum();

        Double saldo = receitas - despesas;

        List<Map<String, Object>> receitasPorCategoria =
                agruparPorCategoria(filtradas, "RECEITA");

        List<Map<String, Object>> despesasPorCategoria =
                agruparPorCategoria(filtradas, "DESPESA");

        return new RelatorioDTO(
                inicio,
                fim,
                receitas,
                despesas,
                saldo,
                filtradas,
                receitasPorCategoria,
                despesasPorCategoria
        );
    }

    public Map<String, Double> resumoFinanceiro(Long usuarioId) {
        List<MovimentacaoDTO> movimentacoes =
                movimentacaoService.listarPorUsuario(usuarioId);

        Double receitas = movimentacoes.stream()
                .filter(mov -> "RECEITA".equalsIgnoreCase(mov.getTipo()))
                .mapToDouble(MovimentacaoDTO::getValor)
                .sum();

        Double despesas = movimentacoes.stream()
                .filter(mov -> "DESPESA".equalsIgnoreCase(mov.getTipo()))
                .mapToDouble(MovimentacaoDTO::getValor)
                .sum();

        Map<String, Double> resumo = new HashMap<>();
        resumo.put("receitas", receitas);
        resumo.put("despesas", despesas);
        resumo.put("saldo", receitas - despesas);

        return resumo;
    }

    public List<MovimentacaoDTO> buscarMovimentacoes(
            Long usuarioId,
            LocalDate inicio,
            LocalDate fim
    ) {
        return movimentacaoService.listarPorUsuario(usuarioId)
                .stream()
                .filter(mov -> filtrarPorPeriodo(mov, inicio, fim))
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> gastosPorCategoria(Long usuarioId) {
        List<MovimentacaoDTO> movimentacoes =
                movimentacaoService.listarPorUsuario(usuarioId);

        return agruparPorCategoria(movimentacoes, "DESPESA");
    }

    private boolean filtrarPorPeriodo(
            MovimentacaoDTO mov,
            LocalDate inicio,
            LocalDate fim
    ) {
        if (inicio == null && fim == null) {
            return true;
        }

        LocalDate data = mov.getData().toLocalDate();

        if (inicio != null && data.isBefore(inicio)) {
            return false;
        }

        if (fim != null && data.isAfter(fim)) {
            return false;
        }

        return true;
    }

    private boolean filtrarPorTipo(MovimentacaoDTO mov, String tipo) {
        if (tipo == null || tipo.isBlank() || "TODOS".equalsIgnoreCase(tipo)) {
            return true;
        }

        return tipo.equalsIgnoreCase(mov.getTipo());
    }

    private boolean filtrarPorOrigem(MovimentacaoDTO mov, String origem) {
        if (origem == null || origem.isBlank() || "TODOS".equalsIgnoreCase(origem)) {
            return true;
        }

        return origem.equalsIgnoreCase(mov.getOrigem());
    }

    private boolean filtrarPorCategoria(MovimentacaoDTO mov, Long categoriaId) {
        if (categoriaId == null) {
            return true;
        }

        return mov.getCategoriaId() != null
                && mov.getCategoriaId().equals(categoriaId);
    }

    private List<Map<String, Object>> agruparPorCategoria(
            List<MovimentacaoDTO> movimentacoes,
            String tipo
    ) {
        Map<String, Double> agrupado = new LinkedHashMap<>();

        movimentacoes.stream()
                .filter(mov -> tipo.equalsIgnoreCase(mov.getTipo()))
                .forEach(mov -> {
                    String categoria = mov.getCategoria() != null
                            ? mov.getCategoria()
                            : "Sem categoria";

                    Double valorAtual = agrupado.getOrDefault(categoria, 0.0);
                    agrupado.put(categoria, valorAtual + mov.getValor());
                });

        Double total = agrupado.values()
                .stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        List<Map<String, Object>> resposta = new ArrayList<>();

        for (Map.Entry<String, Double> entry : agrupado.entrySet()) {
            Map<String, Object> item = new HashMap<>();

            Double percentual = total > 0
                    ? (entry.getValue() / total) * 100
                    : 0.0;

            item.put("categoria", entry.getKey());
            item.put("valor", entry.getValue());
            item.put("percentual", percentual);

            resposta.add(item);
        }

        return resposta;
    }
}