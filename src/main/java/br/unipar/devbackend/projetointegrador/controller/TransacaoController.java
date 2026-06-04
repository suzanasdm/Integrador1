package br.unipar.devbackend.projetointegrador.controller;

import br.unipar.devbackend.projetointegrador.dto.TransacaoUpdateDTO;
import br.unipar.devbackend.projetointegrador.model.Transacao;
import br.unipar.devbackend.projetointegrador.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
@CrossOrigin(origins = "*")
public class TransacaoController {

    private final TransacaoService service;

    public TransacaoController(TransacaoService service) {
        this.service = service;
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Transacao> listarPorUsuario(@PathVariable Long usuarioId) {
        return service.listarPorUsuario(usuarioId);
    }

    @PutMapping("/{id}")
    public Transacao atualizar(
            @PathVariable Long id,
            @Valid @RequestBody TransacaoUpdateDTO dto
    ) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}/usuario/{usuarioId}")
    public void excluir(
            @PathVariable Long id,
            @PathVariable Long usuarioId
    ) {
        service.excluir(id, usuarioId);
    }
}