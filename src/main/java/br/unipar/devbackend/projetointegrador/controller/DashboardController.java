package br.unipar.devbackend.projetointegrador.controller;

import br.unipar.devbackend.projetointegrador.dto.MovimentacaoDTO;
import br.unipar.devbackend.projetointegrador.dto.ResumoCategoriaDTO;
import br.unipar.devbackend.projetointegrador.model.Usuario;
import br.unipar.devbackend.projetointegrador.service.ContaBancariaService;
import br.unipar.devbackend.projetointegrador.service.MovimentacaoService;
import br.unipar.devbackend.projetointegrador.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final MovimentacaoService movimentacaoService;
    private final ContaBancariaService contaBancariaService;
    private final UsuarioService usuarioService;

    public DashboardController(
            MovimentacaoService movimentacaoService,
            ContaBancariaService contaBancariaService,
            UsuarioService usuarioService
    ) {
        this.movimentacaoService = movimentacaoService;
        this.contaBancariaService = contaBancariaService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/me")
    public Map<String, Object> getResumoUsuarioLogado(Authentication authentication) {

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new RuntimeException("Usuário não autenticado.");
        }

        Object principal = authentication.getPrincipal();

        Long usuarioId;


        if (principal instanceof Usuario usuario) {
            usuarioId = usuario.getId();
        }


        else if (principal instanceof String email) {
            Usuario usuario = usuarioService.buscarPorEmail(email);
            usuarioId = usuario.getId();
        }


        else {
            throw new RuntimeException("Não foi possível identificar o usuário autenticado.");
        }

        return montarResumo(usuarioId);
    }


    @GetMapping("/{usuarioId}")
    public Map<String, Object> getResumo(@PathVariable Long usuarioId) {
        return montarResumo(usuarioId);
    }


    private Map<String, Object> montarResumo(Long usuarioId) {

        List<MovimentacaoDTO> movimentacoes =
                movimentacaoService.listarPorUsuario(usuarioId);

        Double totalReceita = movimentacoes.stream()
                .filter(mov -> "RECEITA".equalsIgnoreCase(mov.getTipo()))
                .mapToDouble(MovimentacaoDTO::getValor)
                .sum();

        Double totalDespesa = movimentacoes.stream()
                .filter(mov -> "DESPESA".equalsIgnoreCase(mov.getTipo()))
                .mapToDouble(MovimentacaoDTO::getValor)
                .sum();

        Double saldoMovimentacoes = totalReceita - totalDespesa;

        Double saldoTotalContas =
                contaBancariaService.somarSaldoTotalPorUsuario(usuarioId);

        List<MovimentacaoDTO> ultimasMovimentacoes = movimentacoes;

        if (ultimasMovimentacoes.size() > 10) {
            ultimasMovimentacoes = ultimasMovimentacoes.subList(0, 10);
        }

        List<ResumoCategoriaDTO> receitasPorCategoria =
                gerarResumoPorCategoria(movimentacoes, "RECEITA", totalReceita);

        List<ResumoCategoriaDTO> despesasPorCategoria =
                gerarResumoPorCategoria(movimentacoes, "DESPESA", totalDespesa);

        Map<String, Object> response = new HashMap<>();

        response.put("receita", totalReceita);
        response.put("despesa", totalDespesa);
        response.put("saldoTotal", saldoTotalContas);
        response.put("saldoMovimentacoes", saldoMovimentacoes);
        response.put("transacoes", ultimasMovimentacoes);
        response.put("receitasPorCategoria", receitasPorCategoria);
        response.put("despesasPorCategoria", despesasPorCategoria);

        return response;
    }

    private List<ResumoCategoriaDTO> gerarResumoPorCategoria(
            List<MovimentacaoDTO> movimentacoes,
            String tipo,
            Double total
    ) {
        Map<String, Double> agrupado = new LinkedHashMap<>();

        movimentacoes.stream()
                .filter(mov -> tipo.equalsIgnoreCase(mov.getTipo()))
                .forEach(mov -> {
                    String categoria =
                            mov.getCategoria() != null && !mov.getCategoria().isBlank()
                                    ? mov.getCategoria()
                                    : "Sem categoria";

                    Double valorAtual = agrupado.getOrDefault(categoria, 0.0);

                    agrupado.put(categoria, valorAtual + mov.getValor());
                });

        return agrupado.entrySet()
                .stream()
                .map(entry -> {
                    Double percentual =
                            total != null && total > 0
                                    ? (entry.getValue() / total) * 100
                                    : 0.0;

                    return new ResumoCategoriaDTO(
                            entry.getKey(),
                            entry.getValue(),
                            percentual
                    );
                })
                .toList();
    }
}