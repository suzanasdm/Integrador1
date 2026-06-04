package br.unipar.devbackend.projetointegrador.controller;

import br.unipar.devbackend.projetointegrador.dto.LoginDTO;
import br.unipar.devbackend.projetointegrador.dto.UsuarioDTO;
import br.unipar.devbackend.projetointegrador.dto.UsuarioResponseDTO;
import br.unipar.devbackend.projetointegrador.model.Usuario;
import br.unipar.devbackend.projetointegrador.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(
            @Valid @RequestBody UsuarioDTO dto
    ) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());

        Usuario novoUsuario = usuarioService.cadastrar(usuario);

        return ResponseEntity.ok(toResponseDTO(novoUsuario));
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioResponseDTO> login(
            @Valid @RequestBody LoginDTO loginDTO
    ) {
        Usuario usuarioLogado = usuarioService.login(
                loginDTO.getEmail(),
                loginDTO.getSenha()
        );

        return ResponseEntity.ok(toResponseDTO(usuarioLogado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscar(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);

        return ResponseEntity.ok(toResponseDTO(usuario));
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getDataCadastro()
        );
    }
}