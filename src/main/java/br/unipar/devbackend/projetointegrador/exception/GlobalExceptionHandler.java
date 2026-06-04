package br.unipar.devbackend.projetointegrador.exception;

import br.unipar.devbackend.projetointegrador.dto.ErroResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponseDTO> tratarErroValidacao(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        FieldError fieldError = ex.getBindingResult().getFieldErrors().get(0);

        String mensagem = fieldError.getDefaultMessage();

        ErroResponseDTO erro = new ErroResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                mensagem,
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErroResponseDTO> tratarRuntimeException(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        ErroResponseDTO erro = new ErroResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponseDTO> tratarErroGenerico(
            Exception ex,
            HttpServletRequest request
    ) {
        ErroResponseDTO erro = new ErroResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno no servidor.",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}