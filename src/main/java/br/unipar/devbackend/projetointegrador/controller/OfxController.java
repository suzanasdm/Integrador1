package br.unipar.devbackend.projetointegrador.controller;

import br.unipar.devbackend.projetointegrador.model.OfxArquivo;
import br.unipar.devbackend.projetointegrador.service.OfxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/ofx")

public class OfxController {

    private final OfxService ofxService;

    public OfxController(OfxService ofxService) {
        this.ofxService = ofxService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadOFX(
            @RequestParam("file") MultipartFile file,
            @RequestParam("contaId") Long contaId,
            @RequestParam("usuarioId") Long usuarioId
    ) {
        try {
            ofxService.importarOFX(file, contaId, usuarioId);

            return ResponseEntity.ok("Arquivo OFX importado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.badRequest()
                    .body("Erro ao importar OFX: " + e.getMessage());
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<OfxArquivo>> listarArquivos(
            @PathVariable Long usuarioId
    ) {
        return ResponseEntity.ok(
                ofxService.listarArquivosPorUsuario(usuarioId)
        );
    }

    @DeleteMapping("/{arquivoId}/usuario/{usuarioId}")
    public ResponseEntity<Void> excluirArquivo(
            @PathVariable Long arquivoId,
            @PathVariable Long usuarioId
    ) {
        ofxService.excluirArquivo(arquivoId, usuarioId);

        return ResponseEntity.noContent().build();
    }
}