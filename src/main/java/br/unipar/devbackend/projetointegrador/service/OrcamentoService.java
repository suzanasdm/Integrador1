package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.dto.OrcamentoDTO;
import br.unipar.devbackend.projetointegrador.model.Categoria;
import br.unipar.devbackend.projetointegrador.model.Orcamento;
import br.unipar.devbackend.projetointegrador.model.Usuario;
import br.unipar.devbackend.projetointegrador.repository.CategoriaRepository;
import br.unipar.devbackend.projetointegrador.repository.DespesaRepository;
import br.unipar.devbackend.projetointegrador.repository.OrcamentoRepository;
import br.unipar.devbackend.projetointegrador.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrcamentoService {

    private final OrcamentoRepository orcamentoRepository;
    private final DespesaRepository despesaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;

    // Construtor único para injeção automática via Spring (dispensa o @Autowired manual)
    public OrcamentoService(OrcamentoRepository orcamentoRepository,
                            DespesaRepository despesaRepository,
                            UsuarioRepository usuarioRepository,
                            CategoriaRepository categoriaRepository) {
        this.orcamentoRepository = orcamentoRepository;
        this.despesaRepository = despesaRepository;
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
    }

    /**
     * Busca os orçamentos do usuário e calcula em tempo real o quanto
     * já foi gasto baseado nas despesas registradas para aquela categoria.
     */
    public List<Orcamento> buscarPorUsuario(Long usuarioId) {
        List<Orcamento> orcamentos = orcamentoRepository.findByUsuarioId(usuarioId);

        for (Orcamento o : orcamentos) {
            // Se o seu DespesaRepository não tiver uma query nativa por mês ainda,
            // você pode filtrar trazendo o total geral da categoria provisoriamente:
            Double gasto = despesaRepository.somarTotalPorUsuarioECategoria(usuarioId, o.getCategoria().getId());

            // Trata o retorno nulo para não quebrar o front-end
            o.setValorGasto(gasto != null ? gasto : 0.0);
        }

        return orcamentos;
    }

    /**
     * Salva um novo limite de orçamento para o usuário
     */
    @Transactional
    public Orcamento salvar(OrcamentoDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + dto.getUsuarioId()));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com o ID: " + dto.getCategoriaId()));

        Orcamento orcamento = new Orcamento();
        orcamento.setValorLimite(dto.getValorLimite());
        orcamento.setMesAno(dto.getMesAno());
        orcamento.setUsuario(usuario);
        orcamento.setCategoria(categoria);
        orcamento.setValorGasto(0.0); // O valor real gasto é calculado dinamicamente no método buscar

        return orcamentoRepository.save(orcamento);
    }
}
