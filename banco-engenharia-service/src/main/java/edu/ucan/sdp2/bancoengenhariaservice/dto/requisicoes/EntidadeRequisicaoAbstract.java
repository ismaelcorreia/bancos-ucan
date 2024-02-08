package edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes;

public abstract class EntidadeRequisicaoAbstract<T> {

    protected String mensagemErro;
    public abstract T mapearEntidade();
    public abstract boolean isValido();

    public String getMensagemErro() {
        return mensagemErro;
    }


}
