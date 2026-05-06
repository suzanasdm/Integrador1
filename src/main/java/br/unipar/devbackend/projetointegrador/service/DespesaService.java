package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.dto.DespesaDTO;
import br.unipar.devbackend.projetointegrador.model.Categoria;
import br.unipar.devbackend.projetointegrador.model.Despesa;
import br.unipar.devbackend.projetointegrador.model.Usuario;
import br.unipar.devbackend.projetointegrador.model.ContaBancaria;
import br.unipar.devbackend.projetointegrador.repository.CategoriaRepository;
import br.unipar.devbackend.projetointegrador.repository.DespesaRepository;
import br.unipar.devbackend.projetointegrador.repository.UsuarioRepository;
import br.unipar.devbackend.projetointegrador.repository.ContaBancariaRepository; // Importante
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante

import java.util.List;

@Service
public class DespesaService {

    private final DespesaRepository despesaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final ContaBancariaRepository contaRepository; // Adicionado

    // Construtor único (melhor prática que @Autowired em campos)
    public DespesaService(DespesaRepository despesaRepository,
                          UsuarioRepository usuarioRepository,
                          CategoriaRepository categoriaRepository,
                          ContaBancariaRepository contaRepository) {
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
        return (total != null) ? total : 0.0;
    }

    @Transactional // Garante que se o saldo não atualizar, a despesa não é salva
    public Despesa salvar(DespesaDTO dto) {
        // 1. Busca as entidades relacionadas
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        ContaBancaria conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new RuntimeException("Conta bancária não encontrada"));

        // 2. Cria a entidade Despesa e associa os objetos
        Despesa despesa = new Despesa();
        despesa.setDescricao(dto.getDescricao());
        despesa.setValor(dto.getValor());
        despesa.setData(dto.getData());
        despesa.setUsuario(usuario);
        despesa.setCategoria(categoria);

        // FUNDAMENTAL: Associa o objeto conta para aparecer o nome do banco no front
        despesa.setConta(conta);

        // 3. Lógica Financeira: SUBTRAIR o saldo (Despesa tira dinheiro)
        Double saldoAtual = conta.getSaldo() != null ? conta.getSaldo() : 0.0;
        conta.setSaldo(saldoAtual - dto.getValor());

        // 4. Salva a conta com saldo atualizado e depois a despesa
        contaRepository.save(conta);
        return despesaRepository.save(despesa);
    }
}