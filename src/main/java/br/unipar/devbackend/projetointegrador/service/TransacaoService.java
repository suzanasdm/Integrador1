package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.model.Transacao;
import br.unipar.devbackend.projetointegrador.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository repository;

    public TransacaoService(TransacaoRepository repository) {
        this.repository = repository;
    }

    public List<Transacao> listarPorUsuario(Long usuarioId) {
        return repository.findByUsuarioIdOrderByDataDesc(usuarioId);
    }

    public Transacao salvar(Transacao transacao) {
        return repository.save(transacao);
    }
}