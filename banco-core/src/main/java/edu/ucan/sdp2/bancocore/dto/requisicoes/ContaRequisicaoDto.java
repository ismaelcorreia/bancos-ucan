package edu.ucan.sdp2.bancocore.dto.requisicoes;


import edu.ucan.sdp2.bancocore.entities.ContaBancaria;
import edu.ucan.sdp2.bancocore.entities.Utilizador;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ContaRequisicaoDto extends EntidadeRequisicaoAbstract<ContaBancaria> {
    private double saldo;
    private List<String> representantesId;
    @Override
    public ContaBancaria mapearEntidade() {
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.setSaldoContabilistico(saldo);
        contaBancaria.setSaldoDisponivel(saldo);
        List<Utilizador> utilizadores = representantesId.stream().map(representanteId-> new Utilizador(UUID.fromString(representanteId))).toList();
        contaBancaria.setRepresentantes(utilizadores);
        return contaBancaria;
    }

    @Override
    public boolean isValido() {
        if (representantesId == null || representantesId.isEmpty()) {
            this.mensagemErro="A conta deve ter pelo menos um representante.";
            return false;
        }else {
            for (String representante : representantesId) {
                if (representante == null || representante.isEmpty()) {
                    this.mensagemErro = "Um dos representantes foi mal informado.";
                    return false;
                }
            }
        }
        return true;
    }
}
