package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.dto.DespesaDTO;
import br.unipar.devbackend.projetointegrador.model.Categoria;
import br.unipar.devbackend.projetointegrador.model.CategoriaEnum;
import br.unipar.devbackend.projetointegrador.model.ContaBancaria;
import br.unipar.devbackend.projetointegrador.model.Despesa;
import br.unipar.devbackend.projetointegrador.model.Usuario;
import br.unipar.devbackend.projetointegrador.repository.CategoriaRepository;
import br.unipar.devbackend.projetointegrador.repository.ContaBancariaRepository;
import br.unipar.devbackend.projetointegrador.repository.DespesaRepository;
import br.unipar.devbackend.projetointegrador.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DespesaService {

    private final DespesaRepository despesaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final ContaBancariaRepository contaRepository;

    public DespesaService(
            DespesaRepository despesaRepository,
            UsuarioRepository usuarioRepository,
            CategoriaRepository categoriaRepository,
            ContaBancariaRepository contaRepository
    ) {
        this.despesaRepository = despesaRepository;
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
        this.contaRepository = contaRepository;
    }

    public List<Despesa> buscarPorUsuario(Long usuarioId) {
        return despesaRepository.findByUsuarioId(usuarioId);
    }

    public Double getTotalDespesas(Long usuarioId) {
        Double total = despesaRepository.somarTotalPorUsuario(usuarioId);
        return total != null ? total : 0.0;
    }

    @Transactional
    public Despesa salvar(DespesaDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        ContaBancaria conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new RuntimeException("Conta bancária não encontrada"));

        validarContaDoUsuario(conta, usuario.getId());
        validarCategoriaDoUsuario(categoria, usuario.getId());
        validarCategoriaDespesa(categoria);

        Despesa despesa = new Despesa();
        despesa.setDescricao(dto.getDescricao());
        despesa.setValor(dto.getValor());
        despesa.setData(dto.getData());
        despesa.setUsuario(usuario);
        despesa.setCategoria(categoria);
        despesa.setConta(conta);

        Double saldoAtual = conta.getSaldo() != null ? conta.getSaldo() : 0.0;
        conta.setSaldo(saldoAtual - dto.getValor());

        contaRepository.save(conta);

        return despesaRepository.save(despesa);
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

    private void validarCategoriaDespesa(Categoria categoria) {
        if (categoria.getTipo() != CategoriaEnum.DESPESA) {
            throw new RuntimeException("Para registrar uma despesa, selecione uma categoria do tipo DESPESA.");
        }
    }
}