package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.model.CodigoRecuperacao;
import br.unipar.devbackend.projetointegrador.model.Usuario;
import br.unipar.devbackend.projetointegrador.repository.CodigoRecuperacaoRepository;
import br.unipar.devbackend.projetointegrador.repository.UsuarioRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder; // Adicionado
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AutenticacaoService {

    private final UsuarioRepository usuarioRepository;
    private final CodigoRecuperacaoRepository codigoRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder; // Adicionado aqui


    public AutenticacaoService(UsuarioRepository usuarioRepository,
                               CodigoRecuperacaoRepository codigoRepository,
                               JavaMailSender mailSender,
                               PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.codigoRepository = codigoRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void enviarCodigoRecuperacao(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("E-mail não cadastrado no sistema."));

        codigoRepository.deleteByEmail(email);

        String codigo = String.format("%06d", new Random().nextInt(1000000));

        CodigoRecuperacao novoCodigo = new CodigoRecuperacao(
                email,
                codigo,
                LocalDateTime.now().plusMinutes(15)
        );
        codigoRepository.save(novoCodigo);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Código de Recuperação de Senha - CyberSoft");
        message.setText("Olá, " + usuario.getNome() + "!\n\n" +
                "Você solicitou a redefinição de sua senha de acesso.\n" +
                "Utilize o código de verificação abaixo no sistema:\n\n" +
                "👉 " + codigo + "\n\n" +
                "Atenção: Este código expirará em 15 minutos.\n" +
                "Caso não tenha solicitado, ignore este e-mail.");

        mailSender.send(message);
    }

    @Transactional
    public void alterarSenhaComCodigo(String email, String codigo, String novaSenha) {
        CodigoRecuperacao tokenValido = codigoRepository.findByEmailAndCodigo(email, codigo)
                .orElseThrow(() -> new RuntimeException("Código de verificação incorreto ou inválido."));

        if (tokenValido.getDataExpiracao().isBefore(LocalDateTime.now())) {
            codigoRepository.delete(tokenValido);
            throw new RuntimeException("Este código de verificação expirou. Solicite um novo.");
        }

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Erro ao processar alteração: Usuário não localizado."));


        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);

        codigoRepository.delete(tokenValido);
    }
}