package edu.ucan.sdp2.bancocore.controllers;


import edu.ucan.sdp2.bancocore.dto.Resposta;
import edu.ucan.sdp2.bancocore.dto.respostas.MovimentosRespotaDto;
import edu.ucan.sdp2.bancocore.entities.Movimento;
import edu.ucan.sdp2.bancocore.services.MovimentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/movimentos")
@RequiredArgsConstructor
public class MovimentoController extends ControladorGenerico<Movimento, MovimentosRespotaDto> {


    @Autowired
    public MovimentoController(MovimentoService movimentoService) { this.serviceApi = movimentoService;}

    @GetMapping(value = "/historicos",params = {"contaId"})
    public ResponseEntity<Resposta> getHistoricos(@RequestParam String contaId) {
        return getServiceApi().historicoMovimentos(UUID.fromString(contaId));
    }

    private MovimentoService getServiceApi() {
        return (MovimentoService) serviceApi;
    }

}


