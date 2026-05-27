package br.unipar.devbackend.projetointegrador.repository;

import br.unipar.devbackend.projetointegrador.model.Categoria;
import br.unipar.devbackend.projetointegrador.model.CategoriaEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByUsuarioId(Long usuarioId);

    List<Categoria> findByUsuarioIdAndTipo(Long usuarioId, CategoriaEnum tipo);

    Optional<Categoria> findByUsuarioIdAndTipoAndNomeIgnoreCase(
            Long usuarioId,
            CategoriaEnum tipo,
            String nome
    );
}