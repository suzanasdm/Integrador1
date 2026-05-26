package br.unipar.devbackend.projetointegrador.repository;

import br.unipar.devbackend.projetointegrador.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findByUsuarioId(Long usuarioId);

    @Query("SELECT SUM(d.valor) FROM Despesa d WHERE d.usuario.id = :usuarioId")
    Double somarTotalPorUsuario(@Param("usuarioId") Long usuarioId);

    // ADICIONADO: Soma o total gasto de um usuário filtrando por uma categoria específica
    @Query("SELECT SUM(d.valor) FROM Despesa d WHERE d.usuario.id = :usuarioId AND d.categoria.id = :categoriaId")
    Double somarTotalPorUsuarioECategoria(@Param("usuarioId") Long usuarioId, @Param("categoriaId") Long categoriaId);
}