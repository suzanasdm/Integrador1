package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.dto.TransacaoUpdateDTO;
import br.unipar.devbackend.projetointegrador.model.Categoria;
import br.unipar.devbackend.projetointegrador.model.ContaBancaria;
import br.unipar.devbackend.projetointegrador.model.Transacao;
import br.unipar.devbackend.projetointegrador.repository.CategoriaRepository;
import br.unipar.devbackend.projetointegrador.repository.ContaBancariaRepository;
import br.unipar.devbackend.projetointegrador.repository.TransacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository repository;
    private final CategoriaRepository categoriaRepository;
    private final ContaBancariaRepository contaBancariaRepository;

    public TransacaoService(
            TransacaoRepository repository,
            CategoriaRepository categoriaRepository,
            ContaBancariaRepository contaBancariaRepository
    ) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
        this.contaBancariaRepository = contaBancariaRepository;
    }

    public List<Transacao> listarPorUsuario(Long usuarioId) {
        return repository.findByUsuarioIdOrderByDataDesc(usuarioId);
    }

    @Transactional
    public Transacao atualizar(Long id, TransacaoUpdateDTO dto) {

        Transacao transacao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada."));

        if (transacao.getUsuario() == null || !transacao.getUsuario().getId().equals(dto.getUsuarioId())) {
            throw new RuntimeException("Esta transação não pertence ao usuário informado.");
        }

        Categoria categoria = null;

        if (dto.getCategoriaId() != null) {
            categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada."));

            if (categoria.getUsuario() == null || !categoria.getUsuario().getId().equals(dto.getUsuarioId())) {
                throw new RuntimeException("A categoria não pertence ao usuário informado.");
            }

            if (categoria.getTipo() != dto.getTipo()) {
                throw new RuntimeException("A categoria selecionada não corresponde ao tipo da transação.");
            }
        }

        transacao.setDescricao(dto.getDescricao());
        transacao.setTipo(dto.getTipo());
        transacao.setCategoria(categoria);

        return repository.save(transacao);
    }

    @Transactional
    public void excluir(Long id, Long usuarioId) {

        Transacao transacao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada."));

        if (transacao.getUsuario() == null || !transacao.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("Esta transação não pertence ao usuário informado.");
        }

        desfazerSaldoConta(transacao);

        repository.delete(transacao);
    }

    private void desfazerSaldoConta(Transacao transacao) {

        ContaBancaria conta = transacao.getConta();

        if (conta == null) {
            return;
        }

        Double saldoAtual = conta.getSaldo() != null
                ? conta.getSaldo()
                : 0.0;

        Double valorTransacao = transacao.getValor() != null
                ? transacao.getValor()
                : 0.0;


        conta.setSaldo(saldoAtual - valorTransacao);

        contaBancariaRepository.save(conta);
    }
}