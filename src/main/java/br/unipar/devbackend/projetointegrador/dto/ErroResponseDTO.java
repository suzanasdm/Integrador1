package br.unipar.devbackend.projetointegrador.dto;

import java.time.LocalDateTime;

public class ErroResponseDTO {

    private LocalDateTime timestamp;
    private Integer status;
    private String message;
    private String path;

    public ErroResponseDTO() {
    }

    public ErroResponseDTO(Integer status, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String mensagem) {
        this.message = mensagem;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String caminho) {
        this.path = caminho;
    }
}