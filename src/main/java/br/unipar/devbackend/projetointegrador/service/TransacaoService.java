package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.model.Categoria;
import br.unipar.devbackend.projetointegrador.model.CategoriaEnum;
import br.unipar.devbackend.projetointegrador.model.ContaBancaria;
import br.unipar.devbackend.projetointegrador.model.Transacao;
import br.unipar.devbackend.projetointegrador.repository.CategoriaRepository;
import br.unipar.devbackend.projetointegrador.repository.ContaBancariaRepository;
import br.unipar.devbackend.projetointegrador.repository.TransacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;
    @Autowired private ContaBancariaRepository contaRepository;
    @Autowired private CategoriaRepository categoriaRepository;

    @Transactional
    public Transacao salvar(Transacao transacao, Long contaId, Long categoriaId) {
        ContaBancaria conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));


        if (categoria.getTipo() == CategoriaEnum.DESPESA) {
            conta.setSaldo(conta.getSaldo() - transacao.getValor());
        } else {
            conta.setSaldo(conta.getSaldo() + transacao.getValor());
        }

        transacao.setConta(conta);
        transacao.setCategoria(categoria);

        contaRepository.save(conta);
        return transacaoRepository.save(transacao);
    }
}
