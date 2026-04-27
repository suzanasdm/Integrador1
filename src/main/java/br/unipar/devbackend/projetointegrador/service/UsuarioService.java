package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.model.Usuario;
import br.unipar.devbackend.projetointegrador.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder encoder) {

        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

    public Usuario cadastrar(Usuario usuario) {

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Email já cadastrado!");
        }

        if (!validarSenha(usuario.getSenha())) {
            throw new RuntimeException(
                    "Senha deve conter no mínimo 8 caracteres, letra maiúscula, número e caractere especial."
            );
        }

        usuario.setSenha(
                encoder.encode(usuario.getSenha())
        );

        usuario.setDataCadastro(LocalDateTime.now());

        return usuarioRepository.save(usuario);
    }

    public Usuario login(String email, String senhaDigitada) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        if (!encoder.matches(senhaDigitada, usuario.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        usuario.setSenha(null); // não expõe hash

        return usuario;
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));
    }

    private boolean validarSenha(String senha) {

        if (senha == null) return false;

        String regex =
                "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

        return senha.matches(regex);
    }
}

