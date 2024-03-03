package edu.ucan.sdp2.connecta.model;

import edu.ucan.sdp2.conectacore.enums.TipoOperacao;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@ToString
@Document("transacao_detalhes")
@Data
public class TransacaoDetalhe {
    private Double montante;
    private String moeda;
    private Cliente emissor;
    private Cliente receptor;
    private TipoOperacao tipoOperacao;
    private String descricao;
}
