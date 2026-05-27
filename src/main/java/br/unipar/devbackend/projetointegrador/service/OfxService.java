package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.model.Categoria;
import br.unipar.devbackend.projetointegrador.model.CategoriaEnum;
import br.unipar.devbackend.projetointegrador.model.ContaBancaria;
import br.unipar.devbackend.projetointegrador.model.Transacao;
import br.unipar.devbackend.projetointegrador.repository.CategoriaRepository;
import br.unipar.devbackend.projetointegrador.repository.ContaBancariaRepository;
import br.unipar.devbackend.projetointegrador.repository.TransacaoRepository;
import com.webcohesion.ofx4j.domain.data.ResponseEnvelope;
import com.webcohesion.ofx4j.domain.data.ResponseMessageSet;
import com.webcohesion.ofx4j.domain.data.banking.BankingResponseMessageSet;
import com.webcohesion.ofx4j.io.AggregateUnmarshaller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.time.ZoneId;

@Service
public class OfxService {

    private final TransacaoRepository transacaoRepository;
    private final ContaBancariaRepository contaBancariaRepository;
    private final CategoriaRepository categoriaRepository;

    public OfxService(
            TransacaoRepository transacaoRepository,
            ContaBancariaRepository contaBancariaRepository,
            CategoriaRepository categoriaRepository
    ) {
        this.transacaoRepository = transacaoRepository;
        this.contaBancariaRepository = contaBancariaRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public void importarOFX(
            MultipartFile file,
            Long contaId,
            Long usuarioId
    ) throws Exception {

        ContaBancaria conta = contaBancariaRepository.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada."));

        if (conta.getUsuario() == null || !conta.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("Esta conta bancária não pertence ao usuário logado.");
        }

        AggregateUnmarshaller<ResponseEnvelope> unmarshaller =
                new AggregateUnmarshaller<>(ResponseEnvelope.class);

        InputStreamReader reader =
                new InputStreamReader(file.getInputStream());

        ResponseEnvelope envelope =
                unmarshaller.unmarshal(reader);

        for (ResponseMessageSet messageSet : envelope.getMessageSets()) {

            if (!(messageSet instanceof BankingResponseMessageSet banking)) {
                continue;
            }

            banking.getStatementResponses().forEach(response -> {

                response.getMessage()
                        .getTransactionList()
                        .getTransactions()
                        .forEach(tx -> {

                            String fitId = tx.getId();

                            if (fitId == null || fitId.isBlank()) {
                                fitId = tx.getDatePosted().getTime() + "-" + tx.getAmount();
                            }

                            if (transacaoRepository.existsByFitIdAndContaId(fitId, conta.getId())) {
                                return;
                            }

                            Double valorOriginal = tx.getAmount().doubleValue();

                            CategoriaEnum tipo =
                                    valorOriginal < 0
                                            ? CategoriaEnum.DESPESA
                                            : CategoriaEnum.RECEITA;

                            Categoria categoriaPadrao =
                                    buscarOuCriarCategoriaPadrao(usuarioId, tipo);

                            Transacao transacao = new Transacao();

                            transacao.setDescricao(
                                    tx.getMemo() != null && !tx.getMemo().isBlank()
                                            ? tx.getMemo()
                                            : "Transação OFX"
                            );

                            transacao.setValor(valorOriginal);
                            transacao.setFitId(fitId);
                            transacao.setConta(conta);
                            transacao.setUsuario(conta.getUsuario());
                            transacao.setTipo(tipo);
                            transacao.setCategoria(categoriaPadrao);

                            transacao.setData(
                                    tx.getDatePosted()
                                            .toInstant()
                                            .atZone(ZoneId.systemDefault())
                                            .toLocalDateTime()
                            );

                            transacaoRepository.save(transacao);
                        });
            });
        }
    }

    private Categoria buscarOuCriarCategoriaPadrao(
            Long usuarioId,
            CategoriaEnum tipo
    ) {
        String nomeCategoria =
                tipo == CategoriaEnum.RECEITA
                        ? "OFX - Receita"
                        : "OFX - Despesa";

        return categoriaRepository
                .findByUsuarioIdAndTipoAndNomeIgnoreCase(usuarioId, tipo, nomeCategoria)
                .orElseGet(() -> {
                    Categoria categoria = new Categoria();
                    categoria.setNome(nomeCategoria);
                    categoria.setTipo(tipo);

                    var usuario = new br.unipar.devbackend.projetointegrador.model.Usuario();
                    usuario.setId(usuarioId);

                    categoria.setUsuario(usuario);

                    return categoriaRepository.save(categoria);
                });
    }
}