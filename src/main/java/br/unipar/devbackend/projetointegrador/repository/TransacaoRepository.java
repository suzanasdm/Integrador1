package br.unipar.devbackend.projetointegrador.repository;

import br.unipar.devbackend.projetointegrador.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    boolean existsByFitIdAndContaId(String fitId, Long contaId);

    List<Transacao> findByUsuarioIdOrderByDataDesc(Long usuarioId);

}