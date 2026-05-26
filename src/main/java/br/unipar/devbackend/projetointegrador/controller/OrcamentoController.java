package br.unipar.devbackend.projetointegrador.controller;

import br.unipar.devbackend.projetointegrador.dto.OrcamentoDTO;
import br.unipar.devbackend.projetointegrador.model.Orcamento;
import br.unipar.devbackend.projetointegrador.service.OrcamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orcamentos")
@CrossOrigin(origins = "*")
public class OrcamentoController {

    @Autowired
    private OrcamentoService orcamentoService;

    // GET: http://localhost:8080/api/orcamentos/usuario/{id}
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Orcamento>> buscarPorUsuario(@PathVariable Long usuarioId) {
        List<Orcamento> orcamentos = orcamentoService.buscarPorUsuario(usuarioId);
        return ResponseEntity.ok(orcamentos);
    }

    // POST: http://localhost:8080/api/orcamentos
    @PostMapping
    public ResponseEntity<Orcamento> salvar(@RequestBody OrcamentoDTO dto) {
        Orcamento novoOrcamento = orcamentoService.salvar(dto);
        return ResponseEntity.ok(novoOrcamento);
    }

}
