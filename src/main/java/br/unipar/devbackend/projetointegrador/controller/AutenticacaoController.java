package br.unipar.devbackend.projetointegrador.controller;

import br.unipar.devbackend.projetointegrador.service.AutenticacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AutenticacaoController {

    private final AutenticacaoService authService;

    public AutenticacaoController(AutenticacaoService authService) {
        this.authService = authService;
    }

    @PostMapping("/recuperar-senha")
    public ResponseEntity<String> solicitarCodigo(@RequestBody Map<String, String> payload) {
        try {
            String email = payload.get("email");
            authService.enviarCodigoRecuperacao(email);
            return ResponseEntity.ok("Código de verificação enviado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(@RequestBody Map<String, String> payload) {
        try {
            authService.alterarSenhaComCodigo(
                    payload.get("email"),
                    payload.get("codigo"),
                    payload.get("novaSenha")
            );
            return ResponseEntity.ok("Senha redefinida com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}