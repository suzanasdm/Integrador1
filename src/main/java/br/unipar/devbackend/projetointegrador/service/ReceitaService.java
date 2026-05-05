package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.dto.ReceitaDTO;
import br.unipar.devbackend.projetointegrador.model.Categoria;
import br.unipar.devbackend.projetointegrador.model.Receita;
import br.unipar.devbackend.projetointegrador.model.Usuario;
import br.unipar.devbackend.projetointegrador.model.ContaBancaria;
import br.unipar.devbackend.projetointegrador.repository.CategoriaRepository;
import br.unipar.devbackend.projetointegrador.repository.ReceitaRepository;
import br.unipar.devbackend.projetointegrador.repository.UsuarioRepository;
import br.unipar.devbackend.projetointegrador.repository.ContaBancariaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReceitaService {

    private final ReceitaRepository receitaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final ContaBancariaRepository contaRepository;

    public ReceitaService(ReceitaRepository receitaRepository,
                          UsuarioRepository usuarioRepository,
                          CategoriaRepository categoriaRepository,
                          ContaBancariaRepository contaRepository) {
        this.receitaRepository = receitaRepository;
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
        this.contaRepository = contaRepository;
    }

    @Transactional
    public Receita salvar(ReceitaDTO dto) {
        // 1. Validações de existência
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        ContaBancaria conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new RuntimeException("Conta bancária não encontrada"));

        // 2. Mapeamento do DTO para a Entidade
        Receita receita = new Receita();
        receita.setDescricao(dto.getDescricao());
        receita.setValor(dto.getValor());
        receita.setData(dto.getData());
        receita.setUsuario(usuario);
        receita.setCategoria(categoria);

        // FUNDAMENTAL: Associa o objeto conta completo para evitar o "N/A" no front
        receita.setConta(conta);

        // Opcional: manter o ID bruto se sua entidade ainda usar esse campo
        receita.setContaId(dto.getContaId());

        // 3. Lógica Financeira: Atualização do Saldo
        // Usando Double (Wrapper) conforme seu erro anterior para evitar NullPointer
        Double saldoAtual = conta.getSaldo() != null ? conta.getSaldo() : 0.0;
        conta.setSaldo(saldoAtual + dto.getValor());

        // 4. Persistência (Salva a conta atualizada e a nova receita)
        contaRepository.save(conta);
        return receitaRepository.save(receita);
    }

    // Listagem por usuário (Garante que os relacionamentos venham no JSON)
    public List<Receita> listarPorUsuario(Long usuarioId) {
        return receitaRepository.findByUsuarioId(usuarioId);
    }

    // Cálculo total para o Dashboard
    public Double getTotalReceitas(Long usuarioId) {
        Double total = receitaRepository.somarTotalPorUsuario(usuarioId);
        return (total != null) ? total : 0.0;
    }

    // Método para deletar (Caso precise limpar a tabela como conversamos)
    @Transactional
    public void deletarTudo() {
        receitaRepository.deleteAll();
    }
}