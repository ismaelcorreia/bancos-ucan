package edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes;

import edu.ucan.sdp2.bancoengenhariaservice.models.Endereco;
import edu.ucan.sdp2.bancoengenhariaservice.models.Utilizador;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EnderecoRequisicaoDto  extends EntidadeRequisicaoAbstract<Endereco> {
    private String endereco;
    @Override
    public Endereco mapearEntidade() {
        return null;
    }

    @Override
    public boolean isValido() {
        if (endereco == null || endereco.isBlank()) {
            this.mensagemErro="O endereço deve ser preenchido corretamente";
            return false;
        }
        return true;
    }
}
