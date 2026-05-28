package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.model.CategoriaEnum;
import br.unipar.devbackend.projetointegrador.model.Transacao;
import br.unipar.devbackend.projetointegrador.repository.TransacaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RelatorioService {

    @Autowired
    private TransacaoRepository repository;

    // RELATÓRIO DE GASTOS POR CATEGORIA
    public List<Map<String, Object>> gastosPorCategoria(Long usuarioId) {

        List<Object[]> dados =
                repository.relatorioGastosPorCategoria(
                        usuarioId,
                        CategoriaEnum.DESPESA
                );

        List<Map<String, Object>> resposta = new ArrayList<>();

        for (Object[] item : dados) {

            Map<String, Object> map = new HashMap<>();

            map.put("categoria", item[0]);
            map.put("valor", item[1]);

            resposta.add(map);
        }

        return resposta;
    }

    // BUSCAR TRANSAÇÕES COM FILTRO
    public List<Transacao> buscarTransacoes(
            Long usuarioId,
            LocalDateTime inicio,
            LocalDateTime fim
    ) {

        // SEM FILTRO
        if (inicio == null || fim == null) {

            return repository
                    .findByUsuarioIdOrderByDataDesc(usuarioId);
        }

        // COM FILTRO
        return repository
                .findByUsuarioIdAndDataBetweenOrderByDataDesc(
                        usuarioId,
                        inicio,
                        fim
                );
    }

    // RESUMO FINANCEIRO
    public Map<String, Double> resumoFinanceiro(Long usuarioId) {

        Double receitas =
                repository.somarPorTipo(
                        usuarioId,
                        CategoriaEnum.RECEITA
                );

        Double despesas =
                repository.somarPorTipo(
                        usuarioId,
                        CategoriaEnum.DESPESA
                );

        Map<String, Double> resumo = new HashMap<>();

        resumo.put("receitas", receitas);
        resumo.put("despesas", despesas);
        resumo.put("saldo", receitas - despesas);

        return resumo;
    }
}