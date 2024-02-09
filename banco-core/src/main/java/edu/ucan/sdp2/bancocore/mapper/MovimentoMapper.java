package edu.ucan.sdp2.bancocore.mapper;

import edu.ucan.sdp2.bancocore.entities.ContaBancaria;
import edu.ucan.sdp2.bancocore.entities.Movimento;
import edu.ucan.sdp2.bancocore.enums.TipoMovimento;
import edu.ucan.sdp2.conectacore.models.TransacaoConectaDetalhes;

public class MovimentoMapper {


    public static Movimento transacaoConectaDetalhesParaMovimentos(TransacaoConectaDetalhes detalhes) {
        Movimento movimento = new Movimento();
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.setIbanConta(detalhes.getIban());
        movimento.setConta(contaBancaria);
        movimento.setTipoMovimento(TipoMovimento.valueOf(detalhes.getTipoMovimento()));
        movimento.setValorMovimento(detalhes.getValor());
        return movimento;
    }

    public static TransacaoConectaDetalhes movimentoParaTransacaoConectaDetalhe(Movimento movimento) {
        TransacaoConectaDetalhes transacaoConectaDetalhes = new TransacaoConectaDetalhes();
        transacaoConectaDetalhes.setIban(movimento.getConta().getIbanConta());
        transacaoConectaDetalhes.setTipoMovimento(movimento.getTipoMovimento().name());
        transacaoConectaDetalhes.setValor(movimento.getValorMovimento());
        return transacaoConectaDetalhes;
    }
}
