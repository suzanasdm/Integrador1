package br.unipar.devbackend.projetointegrador.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true)
    private String email;
    @JsonIgnore
    @Column(unique = false)
    private String senha;

    private LocalDateTime dataCadastro;


    public Usuario() {
    }

    public Usuario(Long id, String nome, String email, String senha, LocalDateTime dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataCadastro = dataCadastro;
    }



}
