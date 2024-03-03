package edu.ucan.sdp2.conectacore.models;

import edu.ucan.sdp2.conectacore.enums.TipoOperacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoRequisicao implements Serializable {
    private String numeroTransacao;
    private String ibanEmissor;
    private String ibanDestinatario;
    private TipoOperacao tipo;
    private String descricao;
    private Double montante;
}
