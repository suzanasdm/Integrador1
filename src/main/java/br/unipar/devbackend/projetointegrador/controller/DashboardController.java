package br.unipar.devbackend.projetointegrador.controller;

import br.unipar.devbackend.projetointegrador.dto.MovimentacaoDTO;
import br.unipar.devbackend.projetointegrador.service.DespesaService;
import br.unipar.devbackend.projetointegrador.service.MovimentacaoService;
import br.unipar.devbackend.projetointegrador.service.ReceitaService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final ReceitaService receitaService;
    private final DespesaService despesaService;
    private final MovimentacaoService movimentacaoService;

    public DashboardController(
            ReceitaService receitaService,
            DespesaService despesaService,
            MovimentacaoService movimentacaoService
    ) {
        this.receitaService = receitaService;
        this.despesaService = despesaService;
        this.movimentacaoService = movimentacaoService;
    }

    @GetMapping("/{usuarioId}")
    public Map<String, Object> getResumo(@PathVariable Long usuarioId) {

        Double totalReceita = receitaService.getTotalReceitas(usuarioId);
        Double totalDespesa = despesaService.getTotalDespesas(usuarioId);

        List<MovimentacaoDTO> movimentacoes =
                movimentacaoService.listarPorUsuario(usuarioId);

        if (movimentacoes.size() > 10) {
            movimentacoes = movimentacoes.subList(0, 10);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("receita", totalReceita);
        response.put("despesa", totalDespesa);
        response.put("saldoTotal", totalReceita - totalDespesa);
        response.put("transacoes", movimentacoes);

        return response;
    }
}