package br.unipar.devbackend.projetointegrador.dto;

import java.time.LocalDateTime;

public class LoginResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private LocalDateTime dataCadastro;
    private String token;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(
            Long id,
            String nome,
            String email,
            LocalDateTime dataCadastro,
            String token
    ) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.dataCadastro = dataCadastro;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public String getToken() {
        return token;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public void setToken(String token) {
        this.token = token;
    }
}