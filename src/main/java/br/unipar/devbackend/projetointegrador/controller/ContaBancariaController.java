package br.unipar.devbackend.projetointegrador.controller;

import br.unipar.devbackend.projetointegrador.dto.ContaBancariaDTO;
import br.unipar.devbackend.projetointegrador.model.BancoEnum;
import br.unipar.devbackend.projetointegrador.model.ContaBancaria;
import br.unipar.devbackend.projetointegrador.service.ContaBancariaService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contas")
public class ContaBancariaController {
    @Autowired
private ContaBancariaService service;

    @PostMapping
    public ContaBancaria salvar(@Valid @RequestBody ContaBancariaDTO dto) {
        ContaBancaria conta = new ContaBancaria();
        conta.setBanco(BancoEnum.NUBANK);
        conta.setBanco(BancoEnum.ITAU);
        conta.setAgencia(dto.getAgencia());
        conta.setNumeroConta(dto.getNumeroConta());
        conta.setSaldo(dto.getSaldo());

        return service.cadastrar(conta, dto.getUsuarioId());
    }


}
