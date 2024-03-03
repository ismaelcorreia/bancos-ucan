package edu.ucan.sdp2.bancocore.mapper;


import edu.ucan.sdp2.bancocore.entities.Transacao;
import edu.ucan.sdp2.conectacore.enums.TipoOperacao;
import edu.ucan.sdp2.conectacore.models.TransacaoRequisicao;

public class TransacaoMapper {


    public static TransacaoRequisicao transacaoToTrasancaoRequisicao(Transacao transacao) {
        TransacaoRequisicao transacaoRequisicao = new TransacaoRequisicao();
        transacaoRequisicao.setNumeroTransacao(transacao.getId().toString());
        transacaoRequisicao.setDescricao(transacao.getTipoOperacao().name() + " externa");
        transacaoRequisicao.setTipo(TipoOperacao.valueOf(transacao.getTipoOperacao().name()));
        transacaoRequisicao.setIbanDestinatario(transacao.getContaDestino());
        transacaoRequisicao.setMontante(transacao.getMovimento().getValorMovimento());
        transacaoRequisicao.setIbanEmissor(transacao.getMovimento().getConta().getIbanConta());
        return transacaoRequisicao;
    }


}
