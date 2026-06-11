package br.unipar.devbackend.projetointegrador.controller;

import br.unipar.devbackend.projetointegrador.dto.MetaRequestDTO;
import br.unipar.devbackend.projetointegrador.dto.MetaResponseDTO;
import br.unipar.devbackend.projetointegrador.service.MetaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metas")
@CrossOrigin(origins = "*")
public class MetaController {

    private final MetaService metaService;

    public MetaController(MetaService metaService) {
        this.metaService = metaService;
    }

    @PostMapping
    public MetaResponseDTO salvar(@RequestBody MetaRequestDTO dto) {
        return metaService.salvar(dto);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<MetaResponseDTO> listarPorUsuario(@PathVariable Long usuarioId) {
        return metaService.listarPorUsuario(usuarioId);
    }

    @PutMapping("/{id}")
    public MetaResponseDTO atualizar(
            @PathVariable Long id,
            @RequestBody MetaRequestDTO dto
    ) {
        return metaService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        metaService.deletar(id);
    }
}