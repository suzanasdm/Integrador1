package br.unipar.devbackend.projetointegrador.dto;

public class OrcamentoResponseDTO {

    private Long id;
    private Long categoriaId;
    private String categoriaNome;
    private String mesAno;
    private Double valorLimite;
    private Double valorGasto;
    private Double valorRestante;
    private Double percentualUsado;
    private String status;

    public OrcamentoResponseDTO() {
    }

    public OrcamentoResponseDTO(
            Long id,
            Long categoriaId,
            String categoriaNome,
            String mesAno,
            Double valorLimite,
            Double valorGasto,
            Double valorRestante,
            Double percentualUsado,
            String status
    ) {
        this.id = id;
        this.categoriaId = categoriaId;
        this.categoriaNome = categoriaNome;
        this.mesAno = mesAno;
        this.valorLimite = valorLimite;
        this.valorGasto = valorGasto;
        this.valorRestante = valorRestante;
        this.percentualUsado = percentualUsado;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public String getMesAno() {
        return mesAno;
    }

    public Double getValorLimite() {
        return valorLimite;
    }

    public Double getValorGasto() {
        return valorGasto;
    }

    public Double getValorRestante() {
        return valorRestante;
    }

    public Double getPercentualUsado() {
        return percentualUsado;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public void setCategoriaNome(String categoriaNome) {
        this.categoriaNome = categoriaNome;
    }

    public void setMesAno(String mesAno) {
        this.mesAno = mesAno;
    }

    public void setValorLimite(Double valorLimite) {
        this.valorLimite = valorLimite;
    }

    public void setValorGasto(Double valorGasto) {
        this.valorGasto = valorGasto;
    }

    public void setValorRestante(Double valorRestante) {
        this.valorRestante = valorRestante;
    }

    public void setPercentualUsado(Double percentualUsado) {
        this.percentualUsado = percentualUsado;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}