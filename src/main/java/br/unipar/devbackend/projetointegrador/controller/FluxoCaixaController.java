package br.unipar.devbackend.projetointegrador.controller;

import br.unipar.devbackend.projetointegrador.dto.FluxoCaixaDTO;
import br.unipar.devbackend.projetointegrador.service.FluxoCaixaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/fluxo-caixa")

public class FluxoCaixaController {

    private final FluxoCaixaService fluxoCaixaService;

    public FluxoCaixaController(FluxoCaixaService fluxoCaixaService) {
        this.fluxoCaixaService = fluxoCaixaService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public FluxoCaixaDTO gerarFluxoCaixa(
            @PathVariable Long usuarioId,

            @RequestParam("inicio")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate inicio,

            @RequestParam("fim")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fim
    ) {
        return fluxoCaixaService.gerarFluxoCaixa(usuarioId, inicio, fim);
    }
}