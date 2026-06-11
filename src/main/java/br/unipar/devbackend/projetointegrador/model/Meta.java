package br.unipar.devbackend.projetointegrador.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "metas")
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Double valorObjetivo;

    @Column(nullable = false)
    private Double valorAtual = 0.0;

    @Column(nullable = false)
    private LocalDate prazo;

    @Column(nullable = false)
    private String prioridade;

    @Column(nullable = false)
    private String status;

    private LocalDateTime dataCadastro;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @PrePersist
    public void prePersist() {
        this.dataCadastro = LocalDateTime.now();

        if (this.valorAtual == null) {
            this.valorAtual = 0.0;
        }

        atualizarStatus();
    }

    @PreUpdate
    public void preUpdate() {
        atualizarStatus();
    }

    public void atualizarStatus() {
        if (this.valorAtual != null && this.valorObjetivo != null && this.valorAtual >= this.valorObjetivo) {
            this.status = "CONCLUIDA";
        } else if (this.prazo != null && this.prazo.isBefore(LocalDate.now())) {
            this.status = "ATRASADA";
        } else {
            this.status = "EM_ANDAMENTO";
        }
    }

}