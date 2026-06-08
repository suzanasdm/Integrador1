package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.model.ContaBancaria;
import br.unipar.devbackend.projetointegrador.model.Usuario;
import br.unipar.devbackend.projetointegrador.repository.ContaBancariaRepository;
import br.unipar.devbackend.projetointegrador.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaBancariaService {

    private final ContaBancariaRepository contaBancariaRepository;
    private final UsuarioRepository usuarioRepository;

    public ContaBancariaService(
            ContaBancariaRepository contaBancariaRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.contaBancariaRepository = contaBancariaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<ContaBancaria> buscarPorUsuario(Long usuarioId) {
        return contaBancariaRepository.findByUsuarioId(usuarioId);
    }

    public ContaBancaria cadastrar(ContaBancaria conta, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        conta.setUsuario(usuario);

        if (conta.getSaldo() == null) {
            conta.setSaldo(0.0);
        }

        return contaBancariaRepository.save(conta);
    }

    public Double somarSaldoTotalPorUsuario(Long usuarioId) {
        List<ContaBancaria> contas = contaBancariaRepository.findByUsuarioId(usuarioId);

        return contas.stream()
                .mapToDouble(conta -> conta.getSaldo() != null ? conta.getSaldo() : 0.0)
                .sum();
    }
}