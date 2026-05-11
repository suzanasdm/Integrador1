package br.unipar.devbackend.projetointegrador.repository;

import br.unipar.devbackend.projetointegrador.model.Transacao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository
        extends JpaRepository<Transacao, Long> {

    boolean existsByFitId(String fitId);

}