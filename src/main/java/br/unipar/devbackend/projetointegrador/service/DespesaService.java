package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.dto.DespesaDTO;
import br.unipar.devbackend.projetointegrador.model.Despesa;
import br.unipar.devbackend.projetointegrador.model.Usuario;
import br.unipar.devbackend.projetointegrador.repository.DespesaRepository;
import br.unipar.devbackend.projetointegrador.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DespesaService {
    @Autowired
    private DespesaRepository despesaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Despesa> buscarPorUsuario(Long usuarioId) {
        return despesaRepository.findByUsuarioId(usuarioId);
    }

    public Double getTotalDespesas(Long usuarioId) {
        Double total = despesaRepository.somarTotalPorUsuario(usuarioId);
        return (total != null) ? total : 0.0;
    }

    public Despesa salvar(DespesaDTO dto) {
        Despesa despesa = new Despesa();
        despesa.setDescricao(dto.getDescricao());
        despesa.setValor(dto.getValor());
        despesa.setData(dto.getData());

        usuarioRepository.findById(dto.getUsuarioId()).ifPresent(despesa::setUsuario);


        return despesaRepository.save(despesa);
    }
}