package br.unipar.devbackend.projetointegrador.controller;

import br.unipar.devbackend.projetointegrador.service.DespesaService;
import br.unipar.devbackend.projetointegrador.service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {
    @Autowired
    private ReceitaService receitaService;
    @Autowired
    private DespesaService despesaService;

    @GetMapping("/{usuarioId}")
    public Map<String, Object> getResumo(@PathVariable Long usuarioId) {
        Double totalReceita = receitaService.getTotalReceitas(usuarioId);
        Double totalDespesa = despesaService.getTotalDespesas(usuarioId);

        Map<String, Object> response = new HashMap<>();
        response.put("receita", totalReceita);
        response.put("despesa", totalDespesa);
        response.put("saldoTotal", totalReceita - totalDespesa);
        response.put("transacoes", new ArrayList<>());

        return response;
    }
}
