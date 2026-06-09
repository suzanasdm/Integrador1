package br.unipar.devbackend.projetointegrador.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(
        name = "transacao",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"fit_id", "conta_id"})
        }
)
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @Column(nullable = false)
    private Double valor ;

    @ManyToOne
    @JoinColumn(name = "ofx_arquivo_id")
    private OfxArquivo arquivoOfx;



    private LocalDateTime data;

    @Column(name = "fit_id")
    private String fitId;

    @Enumerated(EnumType.STRING)
    private CategoriaEnum tipo;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "conta_id")
    private ContaBancaria conta;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


}