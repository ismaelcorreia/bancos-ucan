package edu.ucan.sdp2.conectacore.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConectaTopico {

    CONECTA("conecta"),
    TRANSFERENCIA_SUCESS("transferencia-sucesso"),
    FINISH_SUCCESS("finish_success"),
    FINISH_FAIL("finish_fail"),
    MOVIMENTOS_SUCCESS("movimentos-success"),
    MOVIMENTOS_FAIL("movimentos-fail"),
    PAGAMENTO_SUCCESS("pagamento-success"),
    PAGAMENTO_FAIL("pagamento-fail");

    //OTHERS TOPICS HERE
    private final String topico;
}
