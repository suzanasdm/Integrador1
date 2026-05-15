package br.unipar.devbackend.projetointegrador.controller;

import br.unipar.devbackend.projetointegrador.service.OfxService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ofx")
@CrossOrigin(origins = "*")
public class OfxController {

    private final OfxService ofxService;

    public OfxController(OfxService ofxService) {
        this.ofxService = ofxService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadOFX(
            @RequestParam("file") MultipartFile file,
            @RequestParam("contaId") Long contaId
    ) {
        try {
            ofxService.importarOFX(file, contaId);
            return ResponseEntity.ok("Arquivo OFX importado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Erro ao importar OFX: " + e.getMessage());
        }
    }
}