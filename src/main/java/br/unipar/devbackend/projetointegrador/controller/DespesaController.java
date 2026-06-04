package br.unipar.devbackend.projetointegrador.controller;

import br.unipar.devbackend.projetointegrador.dto.DespesaDTO;
import br.unipar.devbackend.projetointegrador.model.Despesa;
import br.unipar.devbackend.projetointegrador.service.DespesaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/despesas")
@CrossOrigin(origins = "*")
public class DespesaController {

    private final DespesaService service;

    public DespesaController(DespesaService service) {
        this.service = service;
    }

    @GetMapping("/usuario/{id}")
    public List<Despesa> listar(@PathVariable Long id) {
        return service.buscarPorUsuario(id);
    }

    @PostMapping
    public Despesa salvar(@Valid @RequestBody DespesaDTO dto) {
        return service.salvar(dto);
    }
    @PutMapping("/{id}")
    public Despesa atualizar(
            @PathVariable Long id,
            @Valid @RequestBody DespesaDTO dto
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