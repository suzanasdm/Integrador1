package br.unipar.devbackend.projetointegrador.controller;

import br.unipar.devbackend.projetointegrador.dto.UsuarioDTO;
import br.unipar.devbackend.projetointegrador.model.Usuario;
import br.unipar.devbackend.projetointegrador.service.UsuarioService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> cadastrarUsuario(
            @Valid @RequestBody UsuarioDTO dto) {

        try {

            Usuario usuario = new Usuario();
            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());
            usuario.setSenha(dto.getSenha());

            Usuario novoUsuario =
                    usuarioService.cadastrar(usuario);

            return ResponseEntity.ok(novoUsuario);

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody UsuarioDTO usuario) {

        try {

            Usuario usuarioLogado =
                    usuarioService.login(
                            usuario.getEmail(),
                            usuario.getSenha()
                    );

            return ResponseEntity.ok(usuarioLogado);

        } catch (Exception e) {

            return ResponseEntity.status(401)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(
            @PathVariable Long id) {

        try {
            return ResponseEntity.ok(
                    usuarioService.buscarPorId(id)
            );

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }
    }
}

