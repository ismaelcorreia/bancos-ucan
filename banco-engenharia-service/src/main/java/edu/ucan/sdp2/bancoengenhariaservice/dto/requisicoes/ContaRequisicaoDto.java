package edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes;

import edu.ucan.sdp2.bancoengenhariaservice.models.ContaBancaria;
import edu.ucan.sdp2.bancoengenhariaservice.models.Endereco;
import edu.ucan.sdp2.bancoengenhariaservice.models.Utilizador;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ContaRequisicaoDto extends EntidadeRequisicaoAbstract<ContaBancaria> {
    private String endereco;
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
        }
        return true;
    }
}
