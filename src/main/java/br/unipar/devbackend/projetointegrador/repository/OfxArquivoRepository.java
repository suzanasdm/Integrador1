package br.unipar.devbackend.projetointegrador.repository;

import br.unipar.devbackend.projetointegrador.model.OfxArquivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfxArquivoRepository extends JpaRepository<OfxArquivo, Long> {

    // Verifica se um arquivo com este mesmo conteúdo já foi importado por este usuário
    boolean existsByHashMd5AndUsuarioId(String hashMd5, Long usuarioId);
}