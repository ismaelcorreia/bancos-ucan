package edu.ucan.sdp2.bancocore.controllers;


import edu.ucan.sdp2.bancocore.dto.Resposta;
import edu.ucan.sdp2.bancocore.dto.requisicoes.UtilizadorTelefoneCodigo;
import edu.ucan.sdp2.bancocore.dto.respostas.UtilizadorRespostaDto;
import edu.ucan.sdp2.bancocore.entities.Utilizador;
import edu.ucan.sdp2.bancocore.services.UtilizadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/utilizador")
@RequiredArgsConstructor
public class UtilizadorController extends ControladorGenerico<Utilizador, UtilizadorRespostaDto> {

    @Autowired
    public UtilizadorController(UtilizadorService utilizadorService) { this.serviceApi = utilizadorService;}


    @PostMapping("/activar-conta")
    public ResponseEntity<Resposta> activarMinhaConta(@RequestBody UtilizadorTelefoneCodigo UtilizadorTelefoneCodigo){
        return getServiceApi().activarMinhaConta(UtilizadorTelefoneCodigo.getTelefone(),UtilizadorTelefoneCodigo.getCodigo() );
    }


    private UtilizadorService getServiceApi() {
        return (UtilizadorService) serviceApi;
    }

}


