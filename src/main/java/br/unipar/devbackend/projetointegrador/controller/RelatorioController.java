package br.unipar.devbackend.projetointegrador.controller;

import br.unipar.devbackend.projetointegrador.dto.MovimentacaoDTO;
import br.unipar.devbackend.projetointegrador.dto.RelatorioDTO;
import br.unipar.devbackend.projetointegrador.service.RelatorioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/relatorios")

public class RelatorioController {

    private final RelatorioService service;

    public RelatorioController(RelatorioService service) {
        this.service = service;
    }

    @GetMapping("/resumo/{usuarioId}")
    public Map<String, Double> resumoFinanceiro(
            @PathVariable Long usuarioId
    ) {
        return service.resumoFinanceiro(usuarioId);
    }

    @GetMapping("/gastos-categoria/{usuarioId}")
    public List<Map<String, Object>> gastosCategoria(
            @PathVariable Long usuarioId
    ) {
        return service.gastosPorCategoria(usuarioId);
    }

    @GetMapping("/transacoes/{usuarioId}")
    public List<MovimentacaoDTO> buscarTransacoes(
            @PathVariable Long usuarioId,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate inicio,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fim
    ) {
        return service.buscarMovimentacoes(usuarioId, inicio, fim);
    }

    @GetMapping("/usuario/{usuarioId}")
    public RelatorioDTO gerarRelatorio(
            @PathVariable Long usuarioId,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate inicio,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fim,

            @RequestParam(required = false, defaultValue = "TODOS")
            String tipo,

            @RequestParam(required = false, defaultValue = "TODOS")
            String origem,

            @RequestParam(required = false)
            Long categoriaId
    ) {
        return service.gerarRelatorio(
                usuarioId,
                inicio,
                fim,
                tipo,
                origem,
                categoriaId
        );
    }
}