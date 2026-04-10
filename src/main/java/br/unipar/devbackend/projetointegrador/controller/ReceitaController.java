package br.unipar.devbackend.projetointegrador.controller;

import br.unipar.devbackend.projetointegrador.dto.ReceitaDTO;
import br.unipar.devbackend.projetointegrador.model.Receita;
import br.unipar.devbackend.projetointegrador.service.ReceitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/receitas")

public class ReceitaController {
@Autowired
    private final ReceitaService receitaService;

    public ReceitaController(ReceitaService receitaService) {
        this.receitaService = receitaService;
    }

    @PostMapping
    public ResponseEntity<Receita> salvar(@RequestBody ReceitaDTO dto) {
        return ResponseEntity.ok(receitaService.salvar(dto));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Receita>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(receitaService.listarPorUsuario(usuarioId));
    }
}