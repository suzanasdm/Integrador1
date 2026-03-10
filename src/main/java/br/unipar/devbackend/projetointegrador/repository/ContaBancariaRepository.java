package br.unipar.devbackend.projetointegrador.repository;

import br.unipar.devbackend.projetointegrador.model.ContaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContaBancariaRepository  extends JpaRepository < ContaBancaria ,Long> {
    List<ContaBancaria> findByUsuarioId(Long usuarioId);
}
