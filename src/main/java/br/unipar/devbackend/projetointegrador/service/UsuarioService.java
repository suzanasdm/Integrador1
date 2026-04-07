package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.model.Usuario;
import br.unipar.devbackend.projetointegrador.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsuarioService {
    @Autowired
     private UsuarioRepository usuarioRepository;



    public Usuario cadastrar (Usuario usuario) throws Exception {

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new Exception("Email já cadastrado!");
        }

        if (!validarSenha(usuario.getSenha())) {
            throw new Exception("Senha inválida");
        }

        usuario.setDataCadastro(LocalDateTime.now()); // 👈 ESSENCIAL

        return usuarioRepository.save(usuario);
    }

    public Usuario buscar(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
    public Usuario login(String email, String senha) throws Exception {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Usuário não encontrado"));

        if (!usuario.getSenha().equals(senha)) {
            throw new Exception("Senha inválida");
        }

        return usuario;
    }

    private boolean validarSenha(String senha) {
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        return senha.matches(regex);
    }
}
