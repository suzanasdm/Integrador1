package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.dto.MovimentacaoDTO;
import br.unipar.devbackend.projetointegrador.dto.OrcamentoDTO;
import br.unipar.devbackend.projetointegrador.dto.OrcamentoResponseDTO;
import br.unipar.devbackend.projetointegrador.model.Categoria;
import br.unipar.devbackend.projetointegrador.model.CategoriaEnum;
import br.unipar.devbackend.projetointegrador.model.Orcamento;
import br.unipar.devbackend.projetointegrador.model.Usuario;
import br.unipar.devbackend.projetointegrador.repository.CategoriaRepository;
import br.unipar.devbackend.projetointegrador.repository.OrcamentoRepository;
import br.unipar.devbackend.projetointegrador.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class OrcamentoService {

    private final OrcamentoRepository orcamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final MovimentacaoService movimentacaoService;

    public OrcamentoService(
            OrcamentoRepository orcamentoRepository,
            UsuarioRepository usuarioRepository,
            CategoriaRepository categoriaRepository,
            MovimentacaoService movimentacaoService
    ) {
        this.orcamentoRepository = orcamentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
        this.movimentacaoService = movimentacaoService;
    }

    public List<OrcamentoResponseDTO> buscarPorUsuario(Long usuarioId, String mesAno) {
        String mesReferencia = mesAno != null && !mesAno.isBlank()
                ? mesAno
                : YearMonth.now().toString();

        List<Orcamento> orcamentos =
                orcamentoRepository.findByUsuarioIdAndMesAno(usuarioId, mesReferencia);

        return orcamentos.stream()
                .map(orcamento -> montarResponse(orcamento, usuarioId))
                .toList();
    }

    @Transactional
    public OrcamentoResponseDTO salvar(OrcamentoDTO dto) {

        validarMesAno(dto.getMesAno());

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada."));

        validarCategoriaDoUsuario(categoria, usuario.getId());
        validarCategoriaDespesa(categoria);

        YearMonth yearMonth = YearMonth.parse(dto.getMesAno());

        LocalDate periodoInicio = yearMonth.atDay(1);
        LocalDate periodoFim = yearMonth.atEndOfMonth();

        Orcamento orcamento = orcamentoRepository
                .findByUsuarioIdAndCategoriaIdAndMesAno(
                        usuario.getId(),
                        categoria.getId(),
                        dto.getMesAno()
                )
                .orElse(new Orcamento());

        orcamento.setValorLimite(dto.getValorLimite());
        orcamento.setValorGasto(0.0);
        orcamento.setMesAno(dto.getMesAno());
        orcamento.setPeriodoInicio(periodoInicio);
        orcamento.setPeriodoFim(periodoFim);
        orcamento.setUsuario(usuario);
        orcamento.setCategoria(categoria);

        Orcamento salvo = orcamentoRepository.save(orcamento);

        return montarResponse(salvo, usuario.getId());
    }

    private OrcamentoResponseDTO montarResponse(Orcamento orcamento, Long usuarioId) {
        Double valorGasto = calcularValorGasto(
                usuarioId,
                orcamento.getCategoria().getId(),
                orcamento.getMesAno()
        );

        Double valorLimite = orcamento.getValorLimite() != null
                ? orcamento.getValorLimite()
                : 0.0;

        Double valorRestante = valorLimite - valorGasto;

        Double percentualUsado = valorLimite > 0
                ? (valorGasto / valorLimite) * 100
                : 0.0;

        String status;

        if (valorGasto > valorLimite) {
            status = "ULTRAPASSADO";
        } else if (percentualUsado >= 80) {
            status = "ATENCAO";
        } else {
            status = "DENTRO_DO_LIMITE";
        }

        return new OrcamentoResponseDTO(
                orcamento.getId(),
                orcamento.getCategoria().getId(),
                orcamento.getCategoria().getNome(),
                orcamento.getMesAno(),
                valorLimite,
                valorGasto,
                valorRestante,
                percentualUsado,
                status
        );
    }

    private Double calcularValorGasto(Long usuarioId, Long categoriaId, String mesAno) {
        YearMonth yearMonth = YearMonth.parse(mesAno);

        LocalDate inicio = yearMonth.atDay(1);
        LocalDate fim = yearMonth.atEndOfMonth();

        List<MovimentacaoDTO> movimentacoes =
                movimentacaoService.listarPorUsuario(usuarioId);

        return movimentacoes.stream()
                .filter(mov -> "DESPESA".equalsIgnoreCase(mov.getTipo()))
                .filter(mov -> mov.getCategoriaId() != null)
                .filter(mov -> mov.getCategoriaId().equals(categoriaId))
                .filter(mov -> {
                    LocalDate data = mov.getData().toLocalDate();

                    return !data.isBefore(inicio)
                            && !data.isAfter(fim);
                })
                .mapToDouble(MovimentacaoDTO::getValor)
                .sum();
    }

    private void validarCategoriaDoUsuario(Categoria categoria, Long usuarioId) {
        if (categoria.getUsuario() == null || !categoria.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("A categoria não pertence ao usuário informado.");
        }
    }

    private void validarCategoriaDespesa(Categoria categoria) {
        if (categoria.getTipo() != CategoriaEnum.DESPESA) {
            throw new RuntimeException("Orçamentos devem ser configurados apenas para categorias de DESPESA.");
        }
    }

    private void validarMesAno(String mesAno) {
        try {
            YearMonth.parse(mesAno);
        } catch (Exception e) {
            throw new RuntimeException("O mês de vigência deve estar no formato YYYY-MM.");
        }
    }
}