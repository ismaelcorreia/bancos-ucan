package edu.ucan.sdp2.bancocore.dto.requisicoes;

import edu.ucan.sdp2.bancocore.entities.ContaBancaria;
import edu.ucan.sdp2.bancocore.entities.Movimento;
import edu.ucan.sdp2.bancocore.entities.Transacao;
import edu.ucan.sdp2.bancocore.enums.StatusTransacao;
import edu.ucan.sdp2.bancocore.enums.TipoMovimento;
import edu.ucan.sdp2.conectacore.enums.TipoOperacao;
import lombok.Data;

import java.util.UUID;

@Data
public class TransacaoTransferenciaRequisicao extends EntidadeRequisicaoAbstract<Transacao> {

    private Double valor;
    private String contaId;
    private String ibanDestino;



    @Override
    public Transacao mapearEntidade() {
        Transacao transacao = new Transacao();
        transacao.setContaDestino(ibanDestino.trim().toUpperCase());
        transacao.setTipoOperacao(TipoOperacao.Transferencia);
        transacao.setStatusTransacao(StatusTransacao.Pendente);
        Movimento movimento = new Movimento();
        movimento.setValorMovimento(valor);
        movimento.setTipoMovimento(TipoMovimento.Debito);
        movimento.setConta(new ContaBancaria(UUID.fromString(contaId)));
        transacao.setMovimento(movimento);
        return transacao;
    }

    @Override
    public boolean isValido() {
        if (valor <= 0) {
            this.mensagemErro = "O valor deve ser superior à ZERO para proceder a transferência.";
            return false;
        }else if (ibanDestino == null || ibanDestino.isBlank()) {
            this.mensagemErro = "O iban do destino deve ser devidamente informada.";
            return false;
        }else if (contaId == null || contaId.isBlank()) {
            this.mensagemErro = "A conta do movimento deve ser devidamente informada.";
            return false;
        }
        return true;
    }
}
