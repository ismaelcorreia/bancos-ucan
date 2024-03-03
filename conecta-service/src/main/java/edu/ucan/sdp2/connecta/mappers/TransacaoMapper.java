package edu.ucan.sdp2.connecta.mappers;

import edu.ucan.sdp2.conectacore.models.TransacaoRequisicao;
import edu.ucan.sdp2.connecta.dto.transacao.TransacaoStatusResposta;
import edu.ucan.sdp2.connecta.model.Cliente;
import edu.ucan.sdp2.connecta.model.Transacao;
import edu.ucan.sdp2.connecta.model.TransacaoDetalhe;
import org.springframework.stereotype.Component;

@Component
public class TransacaoMapper {

    public TransacaoStatusResposta statusRespostaFromTransacao(Transacao transacao){
        return TransacaoStatusResposta.builder()
                        .transacaoId(transacao.getId().toString())
                        .numero(transacao.getNumero())
                        .dataCriacao(transacao.getDataCriacao())
                        .dataExecucao(transacao.getDataExecucao())
                        .dataUltimaModificacao(transacao.getDataUltimaModificacao())
                        .status(transacao.getStatus())
                        .build();
    }

    public Transacao transacaoFromRequisicao(TransacaoRequisicao requisicao){
        Transacao transacao = new Transacao();
        if (requisicao.getNumeroTransacao() != null) {
            transacao.setNumero(requisicao.getNumeroTransacao());
        }
        TransacaoDetalhe detalhe = new TransacaoDetalhe();
        detalhe.setDescricao(requisicao.getDescricao());
        detalhe.setTipoOperacao(requisicao.getTipo());
        detalhe.setMoeda("AOA");
        detalhe.setMontante(requisicao.getMontante());
        detalhe.setEmissor(Cliente.builder()
                .iban(requisicao.getIbanEmissor())
                .build());
        detalhe.setReceptor(Cliente.builder()
                .iban(requisicao.getIbanDestinatario())
                .build());
        transacao.setDetalhes(detalhe);
        return transacao;
    }

}
