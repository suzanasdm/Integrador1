package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.dto.MovimentacaoDTO;
import br.unipar.devbackend.projetointegrador.model.Despesa;
import br.unipar.devbackend.projetointegrador.model.Receita;
import br.unipar.devbackend.projetointegrador.model.Transacao;
import br.unipar.devbackend.projetointegrador.repository.DespesaRepository;
import br.unipar.devbackend.projetointegrador.repository.ReceitaRepository;
import br.unipar.devbackend.projetointegrador.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MovimentacaoService {
    private final ReceitaRepository receitaRepository;
    private final DespesaRepository despesaRepository;
    private final TransacaoRepository transacaoRepository;

    public MovimentacaoService(ReceitaRepository receitaRepository, DespesaRepository despesaRepository, TransacaoRepository transacaoRepository) {
        this.receitaRepository = receitaRepository;
        this.despesaRepository = despesaRepository;
        this.transacaoRepository = transacaoRepository;
    }

    public List<MovimentacaoDTO> listarPorUsuario(Long usuarioId ){
        List<MovimentacaoDTO> movimentacoes = new ArrayList<>();
        List<Receita> receitas = receitaRepository.findByUsuarioId(usuarioId);
        List<Despesa> despesas = despesaRepository.findByUsuarioId(usuarioId);
        List<Transacao> transacoesOfx = transacaoRepository.findByUsuarioIdOrderByDataDesc(usuarioId);

        for(Receita receita : receitas){
            movimentacoes.add(new MovimentacaoDTO(
                            receita.getId(),
                            receita.getData().atStartOfDay(),
                            receita.getDescricao(),
                            "RECEITA",
                            Math.abs(receita.getValor()),
                            receita.getCategoria() != null ? receita.getCategoria().getNome() : "Sem categoria",
                            receita.getCategoria() != null ? receita.getCategoria().getId() : null,
                            receita.getConta() != null ? receita.getConta().getBanco().name() : "Sem conta",
                            receita.getConta() != null ? receita.getConta().getId() : null,
                            "MANUAL"

            ));
        }
        for (Despesa despesa : despesas) {
            movimentacoes.add(
                    new MovimentacaoDTO(
                            despesa.getId(),
                            despesa.getData().atStartOfDay(),
                            despesa.getDescricao(),
                            "DESPESA",
                            Math.abs(despesa.getValor()),
                            despesa.getCategoria() != null ? despesa.getCategoria().getNome() : "Sem categoria",
                            despesa.getCategoria() != null ? despesa.getCategoria().getId() : null,
                            despesa.getConta() != null ? despesa.getConta().getBanco().name() : "Sem conta",
                            despesa.getConta() != null ? despesa.getConta().getId() : null,
                            "MANUAL"
                    ));
        }

        for (Transacao transacao : transacoesOfx) {
            movimentacoes.add(
                    new MovimentacaoDTO(
                            transacao.getId(),
                            transacao.getData(),
                            transacao.getDescricao(),
                            transacao.getTipo() != null ? transacao.getTipo().name() : "DESPESA",
                            Math.abs(transacao.getValor()),
                            transacao.getCategoria() != null ? transacao.getCategoria().getNome() : "Sem categoria",
                            transacao.getCategoria() != null ? transacao.getCategoria().getId() : null,
                            transacao.getConta() != null ? transacao.getConta().getBanco().name() : "Sem conta",
                            transacao.getConta() != null ? transacao.getConta().getId() : null,
                            "OFX"
                    ));
        }

        movimentacoes.sort(
                Comparator.comparing(MovimentacaoDTO::getData).reversed()
        );

        return movimentacoes;
    }
    }

