package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.dto.MetaRequestDTO;
import br.unipar.devbackend.projetointegrador.dto.MetaResponseDTO;
import br.unipar.devbackend.projetointegrador.model.Categoria;
import br.unipar.devbackend.projetointegrador.model.Meta;
import br.unipar.devbackend.projetointegrador.model.Usuario;
import br.unipar.devbackend.projetointegrador.repository.CategoriaRepository;
import br.unipar.devbackend.projetointegrador.repository.MetaRepository;
import br.unipar.devbackend.projetointegrador.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MetaService {

    private final MetaRepository metaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;

    public MetaService(
            MetaRepository metaRepository,
            UsuarioRepository usuarioRepository,
            CategoriaRepository categoriaRepository
    ) {
        this.metaRepository = metaRepository;
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public MetaResponseDTO salvar(MetaRequestDTO dto) {
        validar(dto);

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        Meta meta = new Meta();
        meta.setDescricao(dto.getDescricao());
        meta.setValorObjetivo(dto.getValorObjetivo());
        meta.setValorAtual(dto.getValorAtual() != null ? dto.getValorAtual() : 0.0);
        meta.setPrazo(dto.getPrazo());
        meta.setPrioridade(dto.getPrioridade() != null ? dto.getPrioridade() : "MEDIA");
        meta.setUsuario(usuario);

        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada."));

            if (!categoria.getUsuario().getId().equals(usuario.getId())) {
                throw new RuntimeException("Essa categoria não pertence ao usuário informado.");
            }

            meta.setCategoria(categoria);
        }

        meta.atualizarStatus();

        Meta metaSalva = metaRepository.save(meta);

        return new MetaResponseDTO(metaSalva);
    }

    public List<MetaResponseDTO> listarPorUsuario(Long usuarioId) {
        return metaRepository.findByUsuarioIdOrderByPrazoAsc(usuarioId)
                .stream()
                .map(MetaResponseDTO::new)
                .toList();
    }

    public MetaResponseDTO atualizar(Long id, MetaRequestDTO dto) {
        Meta meta = metaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meta não encontrada."));

        if (dto.getDescricao() == null || dto.getDescricao().isBlank()) {
            throw new RuntimeException("Descrição da meta é obrigatória.");
        }

        if (dto.getValorObjetivo() == null || dto.getValorObjetivo() <= 0) {
            throw new RuntimeException("Valor objetivo deve ser maior que zero.");
        }

        if (dto.getValorAtual() == null || dto.getValorAtual() < 0) {
            throw new RuntimeException("Valor atual não pode ser negativo.");
        }

        if (dto.getPrazo() == null) {
            throw new RuntimeException("Prazo da meta é obrigatório.");
        }

        meta.setDescricao(dto.getDescricao());
        meta.setValorObjetivo(dto.getValorObjetivo());
        meta.setValorAtual(dto.getValorAtual());
        meta.setPrazo(dto.getPrazo());
        meta.setPrioridade(dto.getPrioridade() != null ? dto.getPrioridade() : "MEDIA");

        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada."));

            if (!categoria.getUsuario().getId().equals(meta.getUsuario().getId())) {
                throw new RuntimeException("Essa categoria não pertence ao usuário da meta.");
            }

            meta.setCategoria(categoria);
        } else {
            meta.setCategoria(null);
        }

        meta.atualizarStatus();

        return new MetaResponseDTO(metaRepository.save(meta));
    }

    public void deletar(Long id) {
        if (!metaRepository.existsById(id)) {
            throw new RuntimeException("Meta não encontrada.");
        }

        metaRepository.deleteById(id);
    }

    private void validar(MetaRequestDTO dto) {
        if (dto.getDescricao() == null || dto.getDescricao().isBlank()) {
            throw new RuntimeException("Descrição da meta é obrigatória.");
        }

        if (dto.getValorObjetivo() == null || dto.getValorObjetivo() <= 0) {
            throw new RuntimeException("Valor objetivo deve ser maior que zero.");
        }

        if (dto.getValorAtual() != null && dto.getValorAtual() < 0) {
            throw new RuntimeException("Valor atual não pode ser negativo.");
        }

        if (dto.getPrazo() == null) {
            throw new RuntimeException("Prazo da meta é obrigatório.");
        }

        if (dto.getPrazo().isBefore(LocalDate.now())) {
            throw new RuntimeException("Prazo da meta não pode ser anterior à data atual.");
        }

        if (dto.getUsuarioId() == null) {
            throw new RuntimeException("Usuário é obrigatório.");
        }
    }
}