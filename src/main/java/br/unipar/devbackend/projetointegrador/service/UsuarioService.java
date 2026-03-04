package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.model.Usuario;
import br.unipar.devbackend.projetointegrador.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
     private UsuarioRepository usuarioRepository;

    public Usuario cadastrar (Usuario usuario) throws Exception{
        if (usuarioRepository.existsByemail(usuario.getEmail())) {
            throw  new Exception("Email já cadastrado!");
        }
        if (!validarSenha(usuario.getSenha())) {
            throw new Exception(" A senha deve ter no mínimo 8 caracteres, " +
                    "com letra maiúscula, número e caractere especial.");
        }
 return usuarioRepository.save(usuario);
    }
    private boolean validarSenha(String senha) {
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        return senha.matches(regex);
    }
}
