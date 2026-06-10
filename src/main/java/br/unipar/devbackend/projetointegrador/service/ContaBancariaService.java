package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.dto.ContaBancariaDTO;
import br.unipar.devbackend.projetointegrador.model.ContaBancaria;
import br.unipar.devbackend.projetointegrador.model.Usuario;
import br.unipar.devbackend.projetointegrador.repository.ContaBancariaRepository;
import br.unipar.devbackend.projetointegrador.repository.DespesaRepository;
import br.unipar.devbackend.projetointegrador.repository.ReceitaRepository;
import br.unipar.devbackend.projetointegrador.repository.TransacaoRepository;
import br.unipar.devbackend.projetointegrador.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContaBancariaService {

    private final ContaBancariaRepository contaBancariaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ReceitaRepository receitaRepository;
    private final DespesaRepository despesaRepository;
    private final TransacaoRepository transacaoRepository;

    public ContaBancariaService(
            ContaBancariaRepository contaBancariaRepository,
            UsuarioRepository usuarioRepository,
            ReceitaRepository receitaRepository,
            DespesaRepository despesaRepository,
            TransacaoRepository transacaoRepository
    ) {
        this.contaBancariaRepository = contaBancariaRepository;
        this.usuarioRepository = usuarioRepository;
        this.receitaRepository = receitaRepository;
        this.despesaRepository = despesaRepository;
        this.transacaoRepository = transacaoRepository;
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

    @Transactional
    public ContaBancaria atualizar(Long id, ContaBancariaDTO dto) {
        ContaBancaria conta = contaBancariaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta bancária não encontrada."));

        if (conta.getUsuario() == null || !conta.getUsuario().getId().equals(dto.getUsuarioId())) {
            throw new RuntimeException("Esta conta bancária não pertence ao usuário informado.");
        }

        conta.setBanco(dto.getBanco());
        conta.setAgencia(dto.getAgencia());
        conta.setNumeroConta(dto.getNumeroConta());
        conta.setSaldo(dto.getSaldo());

        return contaBancariaRepository.save(conta);
    }

    @Transactional
    public void excluir(Long id, Long usuarioId) {
        ContaBancaria conta = contaBancariaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta bancária não encontrada."));

        if (conta.getUsuario() == null || !conta.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("Esta conta bancária não pertence ao usuário informado.");
        }

        boolean possuiReceitas = receitaRepository.existsByContaId(id);
        boolean possuiDespesas = despesaRepository.existsByContaId(id);
        boolean possuiTransacoesOfx = transacaoRepository.existsByContaId(id);

        if (possuiReceitas || possuiDespesas || possuiTransacoesOfx) {
            throw new RuntimeException("Esta conta possui movimentações vinculadas e não pode ser excluída.");
        }

        contaBancariaRepository.delete(conta);
    }
}