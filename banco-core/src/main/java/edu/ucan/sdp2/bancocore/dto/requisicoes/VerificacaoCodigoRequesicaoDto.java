package edu.ucan.sdp2.bancocore.dto.requisicoes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificacaoCodigoRequesicaoDto {
    private String phoneNumber;
    private String code;
}
