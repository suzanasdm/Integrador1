package br.unipar.devbackend.projetointegrador.repository;

import br.unipar.devbackend.projetointegrador.model.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {

    List<Orcamento> findByUsuarioId(Long usuarioId);

    List<Orcamento> findByUsuarioIdAndMesAno(Long usuarioId, String mesAno);

    Optional<Orcamento> findByUsuarioIdAndCategoriaIdAndMesAno(
            Long usuarioId,
            Long categoriaId,
            String mesAno
    );
}