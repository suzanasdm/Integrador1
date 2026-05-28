
package br.unipar.devbackend.projetointegrador.repository;

import br.unipar.devbackend.projetointegrador.model.CategoriaEnum;
import br.unipar.devbackend.projetointegrador.model.Transacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    // VERIFICA SE A TRANSAÇÃO OFX JÁ EXISTE
    boolean existsByFitIdAndContaId(
            String fitId,
            Long contaId
    );

    // LISTAR TRANSAÇÕES DO USUÁRIO
    List<Transacao> findByUsuarioIdOrderByDataDesc(
            Long usuarioId
    );

    // FILTRO POR PERÍODO
    List<Transacao> findByUsuarioIdAndDataBetweenOrderByDataDesc(
            Long usuarioId,
            LocalDateTime inicio,
            LocalDateTime fim
    );

    // RELATÓRIO DE GASTOS POR CATEGORIA
    @Query("""
        SELECT
            COALESCE(t.categoria.nome, 'Sem categoria'),
            COALESCE(SUM(t.valor), 0)
        FROM Transacao t
        WHERE t.usuario.id = :usuarioId
        AND t.tipo = :tipo
        GROUP BY t.categoria.nome
        ORDER BY COALESCE(SUM(t.valor), 0) DESC
    """)
    List<Object[]> relatorioGastosPorCategoria(
            @Param("usuarioId") Long usuarioId,
            @Param("tipo") CategoriaEnum tipo
    );

    // SOMA TOTAL POR TIPO
    @Query("""
        SELECT COALESCE(SUM(t.valor), 0)
        FROM Transacao t
        WHERE t.usuario.id = :usuarioId
        AND t.tipo = :tipo
    """)
    Double somarPorTipo(
            @Param("usuarioId") Long usuarioId,
            @Param("tipo") CategoriaEnum tipo
    );

    // BUSCAR TRANSAÇÕES PARA RELATÓRIO COM FILTRO
    @Query("""
        SELECT t
        FROM Transacao t
        WHERE t.usuario.id = :usuarioId
        AND (
            (:inicio IS NULL OR t.data >= :inicio)
            AND
            (:fim IS NULL OR t.data <= :fim)
        )
        ORDER BY t.data DESC
    """)
    List<Transacao> buscarRelatorioTransacoes(
            @Param("usuarioId") Long usuarioId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );
}