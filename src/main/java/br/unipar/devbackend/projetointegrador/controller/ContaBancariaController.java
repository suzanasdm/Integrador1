package br.unipar.devbackend.projetointegrador.controller;

import br.unipar.devbackend.projetointegrador.dto.ContaBancariaDTO;
import br.unipar.devbackend.projetointegrador.model.ContaBancaria;
import br.unipar.devbackend.projetointegrador.service.ContaBancariaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contas")
@CrossOrigin(origins = "*")
public class ContaBancariaController {

    private final ContaBancariaService service;

    public ContaBancariaController(ContaBancariaService service) {
        this.service = service;
    }

    @PostMapping
    public ContaBancaria salvar(@Valid @RequestBody ContaBancariaDTO dto) {
        ContaBancaria conta = new ContaBancaria();

        conta.setBanco(dto.getBanco());
        conta.setAgencia(dto.getAgencia());
        conta.setNumeroConta(dto.getNumeroConta());
        conta.setSaldo(dto.getSaldo());

        return service.cadastrar(conta, dto.getUsuarioId());
    }

    @GetMapping("/usuario/{id}")
    public List<ContaBancaria> buscarPorUsuario(@PathVariable Long id) {
        return service.buscarPorUsuario(id);
    }

    @PutMapping("/{id}")
    public ContaBancaria atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ContaBancariaDTO dto
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