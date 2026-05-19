package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.model.CategoriaEnum;
import br.unipar.devbackend.projetointegrador.model.ContaBancaria;
import br.unipar.devbackend.projetointegrador.model.Transacao;
import br.unipar.devbackend.projetointegrador.repository.ContaBancariaRepository;
import br.unipar.devbackend.projetointegrador.repository.TransacaoRepository;
import com.webcohesion.ofx4j.domain.data.ResponseEnvelope;
import com.webcohesion.ofx4j.domain.data.ResponseMessageSet;
import com.webcohesion.ofx4j.domain.data.banking.BankingResponseMessageSet;
import com.webcohesion.ofx4j.io.AggregateUnmarshaller;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.time.ZoneId;

@Service
public class OfxService {

    private final TransacaoRepository transacaoRepository;
    private final ContaBancariaRepository contaBancariaRepository;

    public OfxService(
            TransacaoRepository transacaoRepository,
            ContaBancariaRepository contaBancariaRepository
    ) {
        this.transacaoRepository = transacaoRepository;
        this.contaBancariaRepository = contaBancariaRepository;
    }

    public void importarOFX(
            MultipartFile file,
            Long contaId,
            Long usuarioId
    ) throws Exception {

        ContaBancaria conta = contaBancariaRepository.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

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

            if (!(messageSet instanceof BankingResponseMessageSet)) {
                continue;
            }

            BankingResponseMessageSet banking =
                    (BankingResponseMessageSet) messageSet;

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

                            Transacao transacao = new Transacao();

                            Double valor = tx.getAmount().doubleValue();

                            transacao.setDescricao(
                                    tx.getMemo() != null && !tx.getMemo().isBlank()
                                            ? tx.getMemo()
                                            : "Transação OFX"
                            );

                            transacao.setValor(valor);
                            transacao.setFitId(fitId);
                            transacao.setConta(conta);
                            transacao.setUsuario(conta.getUsuario());

                            transacao.setData(
                                    tx.getDatePosted()
                                            .toInstant()
                                            .atZone(ZoneId.systemDefault())
                                            .toLocalDateTime()
                            );

                            if (valor < 0) {
                                transacao.setTipo(CategoriaEnum.DESPESA);
                            } else {
                                transacao.setTipo(CategoriaEnum.RECEITA);
                            }

                            transacaoRepository.save(transacao);
                        });
            });
        }
    }
}