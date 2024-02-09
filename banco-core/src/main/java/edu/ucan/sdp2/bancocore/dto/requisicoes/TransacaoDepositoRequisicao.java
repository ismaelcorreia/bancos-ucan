package edu.ucan.sdp2.bancocore.dto.requisicoes;

import edu.ucan.sdp2.bancocore.entities.Movimento;
import edu.ucan.sdp2.bancocore.entities.Transacao;
import edu.ucan.sdp2.bancocore.enums.StatusTransacao;
import edu.ucan.sdp2.bancocore.enums.TipoMovimento;
import edu.ucan.sdp2.conectacore.enums.TipoOperacao;
import lombok.Data;

@Data
public class TransacaoDepositoRequisicao extends EntidadeRequisicaoAbstract<Transacao> {

    private Double valor;
    private String numeroConta;

    @Override
    public Transacao mapearEntidade() {
        Transacao transacao = new Transacao();
        transacao.setTipoOperacao(TipoOperacao.Deposito);
        transacao.setContaDestino(numeroConta);
        transacao.setStatusTransacao(StatusTransacao.Sucesso);
        Movimento movimento = new Movimento();
        movimento.setValorMovimento(valor);
        movimento.setTipoMovimento(TipoMovimento.Credito);
        transacao.setMovimento(movimento);
        return transacao;
    }

    @Override
    public boolean isValido() {
        if (valor <= 0) {
            this.mensagemErro = "O valor deve ser superior à ZERO para proceder a transferência.";
            return false;
        }else if (numeroConta == null || numeroConta.isBlank()) {
            this.mensagemErro = "A conta do movimento deve ser devidamente informada.";
            return false;
        }
        return true;
    }
}
