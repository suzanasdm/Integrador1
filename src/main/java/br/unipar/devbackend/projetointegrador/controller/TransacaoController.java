package br.unipar.devbackend.projetointegrador.controller;

import br.unipar.devbackend.projetointegrador.model.Transacao;
import br.unipar.devbackend.projetointegrador.service.TransacaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacao")
@CrossOrigin(origins = "*")
public class TransacaoController {

    @Autowired
    private TransacaoService service;

    @GetMapping
    public List<Transacao> listar() {

        return service.listar();

    }

    @PostMapping
    public Transacao salvar(
            @RequestBody Transacao transacao) {

        return service.salvar(transacao);

    }

}