package edu.ucan.sdp2.bancocore.dto.requisicoes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetSenhaRequisicaoDto {
    private String phoneNumber;
    private String newPassword;
    private String code;
}
