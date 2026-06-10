package br.unipar.devbackend.projetointegrador.controller;

import br.unipar.devbackend.projetointegrador.dto.MovimentacaoDTO;
import br.unipar.devbackend.projetointegrador.service.MovimentacaoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimentacoes")

public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    public MovimentacaoController(MovimentacaoService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<MovimentacaoDTO> listarPorUsuario(@PathVariable Long usuarioId) {
        return movimentacaoService.listarPorUsuario(usuarioId);
    }
}