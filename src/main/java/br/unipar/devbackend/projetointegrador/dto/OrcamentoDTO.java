package br.unipar.devbackend.projetointegrador.dto;

public class OrcamentoDTO {
    private Double valorLimite;
    private Long categoriaId;
    private Long usuarioId;
    private String mesAno; // Recebe no formato "YYYY-MM" do HTML

    // Getters e Setters
    public Double getValorLimite() { return valorLimite; }
    public void setValorLimite(Double valorLimite) { this.valorLimite = valorLimite; }

    public Long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getMesAno() { return mesAno; }
    public void setMesAno(String mesAno) { this.mesAno = mesAno; }
}
