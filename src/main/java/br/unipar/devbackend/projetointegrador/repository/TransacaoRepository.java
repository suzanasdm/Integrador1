package br.unipar.devbackend.projetointegrador.repository;

import br.unipar.devbackend.projetointegrador.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByContaId(Long contaId);

    List<Transacao> findByCategoriaId(Long categoriaId);
}
