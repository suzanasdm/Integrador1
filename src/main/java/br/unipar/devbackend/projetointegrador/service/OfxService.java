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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.time.ZoneId;

@Service
public class OfxService<MessageSet> {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

    public void importarOFX(
            MultipartFile file,
            Long contaId
    ) throws Exception {

        AggregateUnmarshaller<ResponseEnvelope> unmarshaller =
                new AggregateUnmarshaller<>(ResponseEnvelope.class);

        InputStreamReader reader =
                new InputStreamReader(file.getInputStream());

        ResponseEnvelope envelope =
                unmarshaller.unmarshal(reader);

        ContaBancaria conta =
                contaBancariaRepository.findById(contaId)
                        .orElseThrow(() ->
                                new RuntimeException("Conta não encontrada"));

        for (ResponseMessageSet messageSet : envelope.getMessageSets()) {

            // verifica se é um conjunto bancário
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

                            // evita duplicidade
                            if (transacaoRepository.existsByFitId(fitId)) {
                                return;
                            }

                            Transacao transacao = new Transacao();

                            Double valor =
                                    tx.getAmount().doubleValue();

                            // evita descrição nula
                            transacao.setDescricao(
                                    tx.getMemo() != null
                                            ? tx.getMemo()
                                            : "Transação OFX"
                            );

                            transacao.setValor(valor);

                            transacao.setFitId(fitId);

                            transacao.setConta(conta);

                            transacao.setData(
                                    tx.getDatePosted()
                                            .toInstant()
                                            .atZone(
                                                    ZoneId.systemDefault()
                                            )
                                            .toLocalDateTime()
                            );

                            // RECEITA ou DESPESA
                            if (valor < 0) {

                                transacao.setTipo(
                                        CategoriaEnum.DESPESA
                                );

                            } else {

                                transacao.setTipo(
                                        CategoriaEnum.RECEITA
                                );

                            }

                            transacaoRepository.save(transacao);

                        });

            });

        }

    }

}