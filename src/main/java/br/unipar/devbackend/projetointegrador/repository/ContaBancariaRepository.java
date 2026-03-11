package br.unipar.devbackend.projetointegrador.repository;

import br.unipar.devbackend.projetointegrador.model.ContaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ContaBancariaRepository  extends JpaRepository < ContaBancaria ,Long> {
    List<ContaBancaria> findByUsuarioId(Long usuarioId);
}
