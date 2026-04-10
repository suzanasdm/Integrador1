package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.model.ContaBancaria;
import br.unipar.devbackend.projetointegrador.repository.ContaBancariaRepository;
import br.unipar.devbackend.projetointegrador.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ContaBancariaService {

    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para buscar as contas de um usuário específico
    public List<ContaBancaria> buscarPorUsuario(Long usuarioId) {
        return contaBancariaRepository.findByUsuarioId(usuarioId);
    }

    // Seu método de cadastro já existente
    public ContaBancaria cadastrar(ContaBancaria conta, Long usuarioId) {
        return usuarioRepository.findById(usuarioId).map(usuario -> {
            conta.setUsuario(usuario);
            return contaBancariaRepository.save(conta);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }
}