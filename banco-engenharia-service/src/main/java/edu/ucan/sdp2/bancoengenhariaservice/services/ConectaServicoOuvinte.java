package edu.ucan.sdp2.bancoengenhariaservice.services;

import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.MovimentoRequisicaoDto;
import edu.ucan.sdp2.conectacore.listener.OuvinteAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConectaServicoOuvinte extends OuvinteAbstract {
    private final MovimentoService movimentoService;

    @Autowired
    public ConectaServicoOuvinte(MovimentoService movimentoService) {
        this.movimentoService = movimentoService;
    }

    @Override
    public void escuta(String json) {
        var movimentoRequisicao = MovimentoRequisicaoDto.fromJson(json);
        movimentoService.movimentarConta(movimentoRequisicao);
    }
}
