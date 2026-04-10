package br.unipar.devbackend.projetointegrador.controller;

import br.unipar.devbackend.projetointegrador.dto.DespesaDTO;
import br.unipar.devbackend.projetointegrador.model.Despesa;
import br.unipar.devbackend.projetointegrador.service.DespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/despesas")
@CrossOrigin(origins = "*")
public class DespesaController {
    @Autowired
    private DespesaService service;

    @GetMapping("/usuario/{id}")
    public List<Despesa> listar(@PathVariable Long id) {
        return service.buscarPorUsuario(id);
    }

    @PostMapping
    public Despesa salvar(@RequestBody DespesaDTO dto) {
        return service.salvar(dto);
    }
}
