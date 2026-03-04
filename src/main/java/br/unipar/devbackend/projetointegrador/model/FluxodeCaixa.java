package br.unipar.devbackend.projetointegrador.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "fluxos_caixa")
public class FluxodeCaixa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal entradas;

    private BigDecimal saidas;

    private BigDecimal saldo;

    private LocalDate periodoInicio;

    private LocalDate periodoFim;

    @OneToMany
    @JoinColumn(name = "fluxo_caixa_id")
    private List<Transacao> transacoes;
}
