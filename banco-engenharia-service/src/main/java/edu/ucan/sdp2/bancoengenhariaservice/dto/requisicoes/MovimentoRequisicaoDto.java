package edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes;

import com.google.gson.Gson;
import edu.ucan.sdp2.bancoengenhariaservice.enums.TipoMovimento;
import edu.ucan.sdp2.bancoengenhariaservice.models.ContaBancaria;
import edu.ucan.sdp2.bancoengenhariaservice.models.Movimento;
import edu.ucan.sdp2.bancoengenhariaservice.models.Utilizador;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class MovimentoRequisicaoDto extends EntidadeRequisicaoAbstract<Movimento> implements Serializable {
    private String iban;
    private double valor;
    private TipoMovimento tipoMovimento;
    @Override
    public Movimento mapearEntidade() {
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.setIbanConta(iban);
        Movimento movimento = new Movimento();
        movimento.setValorMovimento(valor);
        movimento.setTipoMovimento(tipoMovimento);
        movimento.setConta(contaBancaria);
        return movimento;
    }

    @Override
    public boolean isValido() {
        if (iban == null || iban.isEmpty()) {
            this.mensagemErro="O iban precisa ser devidamente informada.";
            return false;
        }else if (tipoMovimento == null) {
            this.mensagemErro="O tipo de movimento deve ser informado!";
            return false;
        }else if (valor <= 0) {
            this.mensagemErro="O valor deve ser válido, superior a 0 (ZERO)!";
            return false;
        }
        return true;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static MovimentoRequisicaoDto fromJson(String json) {
        return new Gson().fromJson(json, MovimentoRequisicaoDto.class);
    }
}
