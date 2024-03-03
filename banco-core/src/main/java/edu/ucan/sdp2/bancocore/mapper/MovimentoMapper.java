package edu.ucan.sdp2.bancocore.mapper;

import edu.ucan.sdp2.bancocore.entities.ContaBancaria;
import edu.ucan.sdp2.bancocore.entities.Movimento;
import edu.ucan.sdp2.bancocore.enums.TipoMovimento;
import edu.ucan.sdp2.conectacore.models.TransacaoConectaDetalhes;
import edu.ucan.sdp2.conectacore.models.TransacaoRequisicao;

public class MovimentoMapper {


    public static Movimento transacaoConectaDetalhesParaMovimentos(TransacaoConectaDetalhes detalhes) {
        Movimento movimento = new Movimento();
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.setIbanConta(detalhes.getIbanDestino());
        movimento.setConta(contaBancaria);
//        movimento.setTipoMovimento(detalhes.getTipoOperacao());
        movimento.setValorMovimento(detalhes.getValor());
        return movimento;
    }


}
