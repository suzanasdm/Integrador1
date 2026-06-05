package br.unipar.devbackend.projetointegrador.repository;

import br.unipar.devbackend.projetointegrador.model.CodigoRecuperacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CodigoRecuperacaoRepository extends JpaRepository<CodigoRecuperacao, Long> {

    // Busca o registro combinando o e-mail e o código digitado pelo usuário
    Optional<CodigoRecuperacao> findByEmailAndCodigo(String email, String codigo);

    // Remove códigos antigos para que o banco não acumule lixo eletrônico
    void deleteByEmail(String email);
}