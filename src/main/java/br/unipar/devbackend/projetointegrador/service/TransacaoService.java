package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.dto.TransacaoDTO;
import br.unipar.devbackend.projetointegrador.model.Categoria;
import br.unipar.devbackend.projetointegrador.model.ContaBancaria;
import br.unipar.devbackend.projetointegrador.model.Transacao;
import br.unipar.devbackend.projetointegrador.repository.CategoriaRepository;
import br.unipar.devbackend.projetointegrador.repository.ContaBancariaRepository;
import br.unipar.devbackend.projetointegrador.repository.TransacaoRepository;

import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ContaBancariaRepository contaBancariaRepository;
    private final CategoriaRepository categoriaRepository;

    public TransacaoService(
            TransacaoRepository transacaoRepository,
            ContaBancariaRepository contaBancariaRepository,
            CategoriaRepository categoriaRepository
    ) {
        this.transacaoRepository = transacaoRepository;
        this.contaBancariaRepository = contaBancariaRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Transacao> listar() {
        return transacaoRepository.findAll();
    }

    public Transacao salvar(TransacaoDTO dto) {

        if (dto.getDescricao() == null || dto.getDescricao().isBlank()) {
            throw new RuntimeException("A descrição da transação é obrigatória.");
        }

        if (dto.getValor() == null) {
            throw new RuntimeException("O valor da transação é obrigatório.");
        }

        if (dto.getData() == null) {
            throw new RuntimeException("A data da transação é obrigatória.");
        }

        if (dto.getContaId() == null) {
            throw new RuntimeException("O ID da conta é obrigatório.");
        }

        if (dto.getCategoriaId() == null) {
            throw new RuntimeException("O ID da categoria é obrigatório.");
        }

        ContaBancaria conta = contaBancariaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new RuntimeException("Conta bancária não encontrada."));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada."));

        Transacao transacao = new Transacao();
        transacao.setDescricao(dto.getDescricao());
        transacao.setValor(dto.getValor());
        transacao.setData(dto.getData().atTime(LocalTime.now()));
        transacao.setConta(conta);
        transacao.setCategoria(categoria);

        return transacaoRepository.save(transacao);
    }
}