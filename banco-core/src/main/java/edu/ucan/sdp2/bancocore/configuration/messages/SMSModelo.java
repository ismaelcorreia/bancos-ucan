package edu.ucan.sdp2.bancocore.configuration.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SMSModelo {

    private String telefone;
    private String mensagem;
}
