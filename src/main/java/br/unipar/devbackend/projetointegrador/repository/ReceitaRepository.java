package br.unipar.devbackend.projetointegrador.repository;

import br.unipar.devbackend.projetointegrador.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    List<Receita> findByUsuarioId(Long usuarioId);

    @Query("SELECT SUM(r.valor) FROM Receita r WHERE r.usuario.id = :usuarioId")
    Double somarTotalPorUsuario(@Param("usuarioId") Long usuarioId);
    boolean existsByContaId(Long contaId);
}