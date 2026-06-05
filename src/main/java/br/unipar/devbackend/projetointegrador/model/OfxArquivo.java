package br.unipar.devbackend.projetointegrador.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
        (name = "ofx_arquivos")
public class OfxArquivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String hashMd5;

    private String nomeArquivo;
    private LocalDateTime dataUpload;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getHashMd5() { return hashMd5; }
    public void setHashMd5(String hashMd5) { this.hashMd5 = hashMd5; }

    public String getNomeArquivo() { return nomeArquivo; }
    public void setNomeArquivo(String nomeArquivo) { this.nomeArquivo = nomeArquivo; }

    public LocalDateTime getDataUpload() { return dataUpload; }
    public void setDataUpload(LocalDateTime dataUpload) { this.dataUpload = dataUpload; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
