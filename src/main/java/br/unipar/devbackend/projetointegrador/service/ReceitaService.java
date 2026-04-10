package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.dto.ReceitaDTO;
import br.unipar.devbackend.projetointegrador.model.Categoria;
import br.unipar.devbackend.projetointegrador.model.Receita;
import br.unipar.devbackend.projetointegrador.model.Usuario;
import br.unipar.devbackend.projetointegrador.repository.CategoriaRepository;
import br.unipar.devbackend.projetointegrador.repository.ReceitaRepository;
import br.unipar.devbackend.projetointegrador.repository.UsuarioRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ReceitaService {

    @Autowired
    private final ReceitaRepository receitaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;

    public ReceitaService(ReceitaRepository receitaRepository, UsuarioRepository usuarioRepository, CategoriaRepository categoriaRepository) {
        this.receitaRepository = receitaRepository;
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Receita salvar(ReceitaDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId()).orElseThrow();
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId()).orElseThrow();

        Receita receita = new Receita();
        receita.setDescricao(dto.getDescricao());
        receita.setValor(dto.getValor());
        receita.setUsuario(usuario);
        receita.setCategoria(categoria);
        receita.setData(dto.getData());


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