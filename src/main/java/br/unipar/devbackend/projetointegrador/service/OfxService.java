package br.unipar.devbackend.projetointegrador.service;

import br.unipar.devbackend.projetointegrador.model.*;
import br.unipar.devbackend.projetointegrador.repository.*;
import com.webcohesion.ofx4j.domain.data.ResponseEnvelope;
import com.webcohesion.ofx4j.domain.data.ResponseMessageSet;
import com.webcohesion.ofx4j.domain.data.banking.BankingResponseMessageSet;
import com.webcohesion.ofx4j.io.AggregateUnmarshaller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils; // Biblioteca nativa do Spring para MD5
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class OfxService {

    private final TransacaoRepository transacaoRepository;
    private final ContaBancariaRepository contaBancariaRepository;
    private final CategoriaRepository categoriaRepository;
    private final OfxArquivoRepository ofxArquivoRepository; // Adicionado

    public OfxService(
            TransacaoRepository transacaoRepository,
            ContaBancariaRepository contaBancariaRepository,
            CategoriaRepository categoriaRepository,
            OfxArquivoRepository ofxArquivoRepository
    ) {
        this.transacaoRepository = transacaoRepository;
        this.contaBancariaRepository = contaBancariaRepository;
        this.categoriaRepository = categoriaRepository;
        this.ofxArquivoRepository = ofxArquivoRepository;
    }

    @Transactional
    public void importarOFX(
            MultipartFile file,
            Long contaId,
            Long usuarioId
    ) throws Exception {

        // 1. Verificação de Segurança de Conta
        ContaBancaria conta = contaBancariaRepository.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada."));

        if (conta.getUsuario() == null || !conta.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("Esta conta bancária não pertence ao usuário logado.");
        }

        // 2. NOVO: Validação do Arquivo Único (Hash MD5)
        String hashMd5 = DigestUtils.md5DigestAsHex(file.getBytes());
        if (ofxArquivoRepository.existsByHashMd5AndUsuarioId(hashMd5, usuarioId)) {
            throw new RuntimeException("Este arquivo OFX já foi importado anteriormente.");
        }

        // 3. Processamento do OFX padrão
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

                            // Segurança extra por linha (mantida do seu código)
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

        // 4. NOVO: Salva o registro do arquivo para impedir re-uploads futuros
        OfxArquivo arquivoRegistro = new OfxArquivo();
        arquivoRegistro.setHashMd5(hashMd5);
        arquivoRegistro.setNomeArquivo(file.getOriginalFilename());
        arquivoRegistro.setDataUpload(LocalDateTime.now());
        arquivoRegistro.setUsuario(conta.getUsuario());

        ofxArquivoRepository.save(arquivoRegistro);
    }

    private Categoria buscarOuCriarCategoriaPadrao(Long usuarioId, CategoriaEnum tipo) {
        String nomeCategoria = tipo == CategoriaEnum.RECEITA ? "OFX - Receita" : "OFX - Despesa";

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