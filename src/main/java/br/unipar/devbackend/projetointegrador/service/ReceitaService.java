package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.dto.ReceitaDTO;
import br.unipar.devbackend.projetointegrador.model.Categoria;
import br.unipar.devbackend.projetointegrador.model.CategoriaEnum;
import br.unipar.devbackend.projetointegrador.model.ContaBancaria;
import br.unipar.devbackend.projetointegrador.model.Receita;
import br.unipar.devbackend.projetointegrador.model.Usuario;
import br.unipar.devbackend.projetointegrador.repository.CategoriaRepository;
import br.unipar.devbackend.projetointegrador.repository.ContaBancariaRepository;
import br.unipar.devbackend.projetointegrador.repository.ReceitaRepository;
import br.unipar.devbackend.projetointegrador.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReceitaService {

    private final ReceitaRepository receitaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final ContaBancariaRepository contaRepository;

    public ReceitaService(
            ReceitaRepository receitaRepository,
            UsuarioRepository usuarioRepository,
            CategoriaRepository categoriaRepository,
            ContaBancariaRepository contaRepository
    ) {
        this.receitaRepository = receitaRepository;
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
        this.contaRepository = contaRepository;
    }

    @Transactional
    public Receita salvar(ReceitaDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        ContaBancaria conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new RuntimeException("Conta bancária não encontrada"));

        validarContaDoUsuario(conta, usuario.getId());
        validarCategoriaDoUsuario(categoria, usuario.getId());
        validarCategoriaReceita(categoria);

        Receita receita = new Receita();
        receita.setDescricao(dto.getDescricao());
        receita.setValor(dto.getValor());
        receita.setData(dto.getData());
        receita.setUsuario(usuario);
        receita.setCategoria(categoria);
        receita.setConta(conta);

        Double saldoAtual = conta.getSaldo() != null ? conta.getSaldo() : 0.0;
        conta.setSaldo(saldoAtual + dto.getValor());

        contaRepository.save(conta);

        return receitaRepository.save(receita);
    }

    public List<Receita> listarPorUsuario(Long usuarioId) {
        return receitaRepository.findByUsuarioId(usuarioId);
    }

    public Double getTotalReceitas(Long usuarioId) {
        Double total = receitaRepository.somarTotalPorUsuario(usuarioId);
        return total != null ? total : 0.0;
    }

    private void validarContaDoUsuario(ContaBancaria conta, Long usuarioId) {
        if (conta.getUsuario() == null || !conta.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("A conta bancária não pertence ao usuário informado.");
        }
    }

    private void validarCategoriaDoUsuario(Categoria categoria, Long usuarioId) {
        if (categoria.getUsuario() == null || !categoria.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("A categoria não pertence ao usuário informado.");
        }
    }

    private void validarCategoriaReceita(Categoria categoria) {
        if (categoria.getTipo() != CategoriaEnum.RECEITA) {
            throw new RuntimeException("Para registrar uma receita, selecione uma categoria do tipo RECEITA.");
        }
    }
    @Transactional
    public Receita atualizar(Long id, ReceitaDTO dto) {

        Receita receitaExistente = receitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada."));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        Categoria novaCategoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada."));

        ContaBancaria novaConta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new RuntimeException("Conta bancária não encontrada."));

        validarContaDoUsuario(novaConta, usuario.getId());
        validarCategoriaDoUsuario(novaCategoria, usuario.getId());
        validarCategoriaReceita(novaCategoria);

        if (!receitaExistente.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Esta receita não pertence ao usuário informado.");
        }

        ContaBancaria contaAntiga = receitaExistente.getConta();

        if (contaAntiga != null) {
            Double saldoContaAntiga = contaAntiga.getSaldo() != null ? contaAntiga.getSaldo() : 0.0;
            contaAntiga.setSaldo(saldoContaAntiga - receitaExistente.getValor());
            contaRepository.save(contaAntiga);
        }

        Double saldoNovaConta = novaConta.getSaldo() != null ? novaConta.getSaldo() : 0.0;
        novaConta.setSaldo(saldoNovaConta + dto.getValor());
        contaRepository.save(novaConta);

        receitaExistente.setDescricao(dto.getDescricao());
        receitaExistente.setValor(dto.getValor());
        receitaExistente.setData(dto.getData());
        receitaExistente.setUsuario(usuario);
        receitaExistente.setCategoria(novaCategoria);
        receitaExistente.setConta(novaConta);

        return receitaRepository.save(receitaExistente);
    }

    @Transactional
    public void excluir(Long id, Long usuarioId) {

        Receita receita = receitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada."));

        if (!receita.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("Esta receita não pertence ao usuário informado.");
        }

        ContaBancaria conta = receita.getConta();

        if (conta != null) {
            Double saldoAtual = conta.getSaldo() != null ? conta.getSaldo() : 0.0;
            conta.setSaldo(saldoAtual - receita.getValor());
            contaRepository.save(conta);
        }

        receitaRepository.delete(receita);
    }
}