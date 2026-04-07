package br.unipar.devbackend.projetointegrador.controller;

import br.unipar.devbackend.projetointegrador.dto.CategoriaDTO;
import br.unipar.devbackend.projetointegrador.model.Categoria;
import br.unipar.devbackend.projetointegrador.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public Categoria salvar(@Valid @RequestBody CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        categoria.setTipo(dto.getTipo());

        return categoriaService.cadastrar(categoria, dto.getUsuarioId());
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Categoria> listarPorUsuario(@PathVariable Long usuarioId) {
        return categoriaService.listarPorUsuario(usuarioId);
    }
}
