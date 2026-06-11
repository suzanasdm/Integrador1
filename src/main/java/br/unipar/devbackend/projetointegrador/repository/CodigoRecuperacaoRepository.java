package br.unipar.devbackend.projetointegrador.repository;

import br.unipar.devbackend.projetointegrador.model.CodigoRecuperacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CodigoRecuperacaoRepository extends JpaRepository<CodigoRecuperacao, Long> {


    Optional<CodigoRecuperacao> findByEmailAndCodigo(String email, String codigo);

  
    void deleteByEmail(String email);
}