package br.unipar.devbackend.projetointegrador.controller;

import br.unipar.devbackend.projetointegrador.dto.OrcamentoDTO;
import br.unipar.devbackend.projetointegrador.dto.OrcamentoResponseDTO;
import br.unipar.devbackend.projetointegrador.service.OrcamentoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orcamentos")
@CrossOrigin(origins = "*")
public class OrcamentoController {

    private final OrcamentoService orcamentoService;

    public OrcamentoController(OrcamentoService orcamentoService) {
        this.orcamentoService = orcamentoService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<OrcamentoResponseDTO>> buscarPorUsuario(
            @PathVariable Long usuarioId,
            @RequestParam(required = false) String mesAno
    ) {
        return ResponseEntity.ok(
                orcamentoService.buscarPorUsuario(usuarioId, mesAno)
        );
    }

    @PostMapping
    public ResponseEntity<OrcamentoResponseDTO> salvar(
            @Valid @RequestBody OrcamentoDTO dto
    ) {
        return ResponseEntity.ok(orcamentoService.salvar(dto));
    }
}