package edu.ucan.sdp2.bancocore.controllers;


import edu.ucan.sdp2.bancocore.dto.Resposta;
import edu.ucan.sdp2.bancocore.dto.respostas.ContaRespotaDto;
import edu.ucan.sdp2.bancocore.entities.ContaBancaria;
import edu.ucan.sdp2.bancocore.services.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/contas")
@RequiredArgsConstructor
public class ContaController extends ControladorGenerico<ContaBancaria, ContaRespotaDto> {


    @Autowired
    public ContaController(ContaService contaService) { this.serviceApi = contaService;}


    @GetMapping(params = "iban")
    public ResponseEntity<Resposta> contaPorIban(@RequestParam String iban) {
        return getContaService().contaPorIban(iban);
    }
    @GetMapping(params = "numeroConta")
    public ResponseEntity<Resposta> contaPorConta(@RequestParam String numeroConta) {
        return getContaService().contaPorNumeroConta(numeroConta);
    }

    @GetMapping("/minhas")
    public ResponseEntity<Resposta> minhasContas() {
        return getContaService().minhasContas();
    }


    private ContaService getContaService() {
        return (ContaService)serviceApi;
    }

}


