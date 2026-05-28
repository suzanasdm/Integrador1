package br.unipar.devbackend.projetointegrador.controller;

import br.unipar.devbackend.projetointegrador.model.Transacao;
import br.unipar.devbackend.projetointegrador.service.RelatorioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/relatorios")
@CrossOrigin("*")
public class RelatorioController {

    @Autowired
    private RelatorioService service;

    @GetMapping("/gastos-categoria/{usuarioId}")
    public List<Map<String, Object>> gastosCategoria(
            @PathVariable Long usuarioId
    ) {
        return service.gastosPorCategoria(usuarioId);
    }

    @GetMapping("/transacoes/{usuarioId}")
    public List<Transacao> buscarTransacoes(

            @PathVariable Long usuarioId,

            @RequestParam(required = false) String inicio,

            @RequestParam(required = false) String fim
    ) {

        LocalDateTime dataInicio = null;
        LocalDateTime dataFim = null;

        if (inicio != null && !inicio.isEmpty()) {
            dataInicio = LocalDate.parse(inicio).atStartOfDay();
        }

        if (fim != null && !fim.isEmpty()) {
            dataFim = LocalDate.parse(fim).atTime(23, 59, 59);
        }

        return service.buscarTransacoes(
                usuarioId,
                dataInicio,
                dataFim
        );
    }
}