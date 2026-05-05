package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.dto.ReceitaDTO;
import br.unipar.devbackend.projetointegrador.model.Categoria;
import br.unipar.devbackend.projetointegrador.model.Receita;
import br.unipar.devbackend.projetointegrador.model.Usuario;
import br.unipar.devbackend.projetointegrador.model.ContaBancaria; // Importante
import br.unipar.devbackend.projetointegrador.repository.CategoriaRepository;
import br.unipar.devbackend.projetointegrador.repository.ReceitaRepository;
import br.unipar.devbackend.projetointegrador.repository.UsuarioRepository;
import br.unipar.devbackend.projetointegrador.repository.ContaBancariaRepository; // Importante
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante

import java.util.List;

@Service
public class ReceitaService {

    private final ReceitaRepository receitaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final ContaBancariaRepository contaRepository; // 1. Adicionado

    // Construtor atualizado com o novo Repository
    public ReceitaService(ReceitaRepository receitaRepository,
                          UsuarioRepository usuarioRepository,
                          CategoriaRepository categoriaRepository,
                          ContaBancariaRepository contaRepository) {
        this.receitaRepository = receitaRepository;
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
        this.contaRepository = contaRepository;
    }

    @Transactional // 2. Garante a integridade da operação financeira
    public Receita salvar(ReceitaDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        // 3. Buscar a Conta Bancária onde o dinheiro vai entrar
        ContaBancaria conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new RuntimeException("Conta bancária não encontrada"));

        Receita receita = new Receita();
        receita.setDescricao(dto.getDescricao());
        receita.setValor(dto.getValor());
        receita.setUsuario(usuario);
        receita.setCategoria(categoria);
        receita.setData(dto.getData());
        receita.setContaId(dto.getContaId());
        // Se a sua entidade Receita tiver o campo conta, adicione:
        // receita.setConta(conta);

        // 4. ATUALIZAR O SALDO (A parte que faltava)
        // Se o saldo for Double:
        Double novoSaldo = conta.getSaldo() + dto.getValor();
        conta.setSaldo(novoSaldo);

        // 5. Salvar a conta com o saldo novo
        contaRepository.save(conta);

        // 6. Salvar a receita
        return receitaRepository.save(receita);
    }

    public List<Receita> listarPorUsuario(Long usuarioId) {
        return receitaRepository.findByUsuarioId(usuarioId);
    }

    public Double getTotalReceitas(Long usuarioId) {
        Double total = receitaRepository.somarTotalPorUsuario(usuarioId);
        return (total != null) ? total : 0.0;
    }
}