package br.unipar.devbackend.projetointegrador.repository;

import br.unipar.devbackend.projetointegrador.model.OfxArquivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfxArquivoRepository extends JpaRepository<OfxArquivo, Long> {


    boolean existsByHashMd5AndUsuarioId(String hashMd5, Long usuarioId);
    List<OfxArquivo> findByUsuarioIdOrderByDataUploadDesc(Long usuarioId);

}