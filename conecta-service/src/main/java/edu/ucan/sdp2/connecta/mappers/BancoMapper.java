package edu.ucan.sdp2.connecta.mappers;

import edu.ucan.sdp2.connecta.dto.banco.BancoCadastroRequisicao;
import edu.ucan.sdp2.connecta.dto.banco.BancoResposta;
import edu.ucan.sdp2.connecta.model.Banco;
import org.springframework.stereotype.Component;

@Component
public class BancoMapper {

    public Banco getBancoFromCadastroRequisicao(BancoCadastroRequisicao requisicao) {
        Banco banco = new Banco();
        banco.setNome(requisicao.getNome().toUpperCase());
        banco.setSigla(requisicao.getSigla().toUpperCase());
        banco.setChave(requisicao.getChave().toUpperCase());
        banco.setCodigoIBAN(requisicao.getCodigoIBAN().toUpperCase());
        return banco;
    }

    public BancoResposta getBancoRespostaFromBanco(Banco banco) {
        return BancoResposta.builder()
                .id(banco.getId())
                .nome(banco.getNome())
                .sigla(banco.getSigla())
                .codigoIBAN(banco.getCodigoIBAN())
                .canal(banco.getCanal())
                .build();
    }

    public BancoResposta getBancoRespostaMiniFromBanco(Banco banco) {
        return BancoResposta.builder()
                .id(banco.getId())
                .nome(banco.getNome())
                .sigla(banco.getSigla())
                .build();
    }
}
