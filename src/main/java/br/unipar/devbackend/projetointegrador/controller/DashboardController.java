package br.unipar.devbackend.projetointegrador.controller;

import br.unipar.devbackend.projetointegrador.dto.DashboardTransacaoDTO;
import br.unipar.devbackend.projetointegrador.model.Despesa;
import br.unipar.devbackend.projetointegrador.model.Receita;
import br.unipar.devbackend.projetointegrador.service.DespesaService;
import br.unipar.devbackend.projetointegrador.service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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

        List<Receita> receitas = receitaService.listarPorUsuario(usuarioId);
        List<Despesa> despesas = despesaService.buscarPorUsuario(usuarioId);

        List<DashboardTransacaoDTO> transacoes = new ArrayList<>();

        for (Receita receita : receitas) {
            transacoes.add(
                    new DashboardTransacaoDTO(
                            receita.getData(),
                            receita.getDescricao(),
                            receita.getCategoria() != null ? receita.getCategoria().getNome() : "Sem categoria",
                            "RECEITA",
                            receita.getValor()
                    )
            );
        }

        for (Despesa despesa : despesas) {
            transacoes.add(
                    new DashboardTransacaoDTO(
                            despesa.getData(),
                            despesa.getDescricao(),
                            despesa.getCategoria() != null ? despesa.getCategoria().getNome() : "Sem categoria",
                            "DESPESA",
                            despesa.getValor()
                    )
            );
        }

        transacoes.sort(
                Comparator.comparing(DashboardTransacaoDTO::getData).reversed()
        );

        if (transacoes.size() > 10) {
            transacoes = transacoes.subList(0, 10);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("receita", totalReceita);
        response.put("despesa", totalDespesa);
        response.put("saldoTotal", totalReceita - totalDespesa);
        response.put("transacoes", transacoes);

        return response;
    }
}