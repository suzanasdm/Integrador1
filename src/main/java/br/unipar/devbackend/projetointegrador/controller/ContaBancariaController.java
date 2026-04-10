package br.unipar.devbackend.projetointegrador.controller;

import br.unipar.devbackend.projetointegrador.dto.ContaBancariaDTO;
import br.unipar.devbackend.projetointegrador.model.BancoEnum;
import br.unipar.devbackend.projetointegrador.model.ContaBancaria;
import br.unipar.devbackend.projetointegrador.service.ContaBancariaService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contas")
public class ContaBancariaController {
    @Autowired
    private ContaBancariaService service;

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
        // Você precisa ter esse método 'buscarPorUsuario' criado no seu Service
        return service.buscarPorUsuario(id);
    }
}
