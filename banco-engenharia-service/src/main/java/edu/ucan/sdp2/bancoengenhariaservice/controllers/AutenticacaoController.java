package edu.ucan.sdp2.bancoengenhariaservice.controllers;

import edu.ucan.sdp2.bancoengenhariaservice.dto.Resposta;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.ResetSenhaRequisicaoDto;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.UtilizadorAutoRegistoDto;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.UtilizadorLoginRequisicaoDto;
import edu.ucan.sdp2.bancoengenhariaservice.models.TelefoneVerificacao;
import edu.ucan.sdp2.bancoengenhariaservice.services.AutenticacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/autenticacao")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final AutenticacaoService service;

    @PostMapping(value = "/verificar_telemovel")
    public ResponseEntity<Resposta> verificarTelemovel(@RequestParam String phone){
        return  service.enviarCodigoVerificacao(phone);
    }

    @Secured({"ROLE_USER"})
    @PostMapping(value = "/confirmar-codigo")
    public ResponseEntity<Resposta> confirmarCodigo(@RequestBody TelefoneVerificacao dto){
        return  service.verificacaoCodigoTelefone(dto.getTelefone(), dto.getCodigo());
    }
    @PostMapping(value= "/registar", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Resposta> register(
             UtilizadorAutoRegistoDto dto,
             @RequestPart MultipartFile ficheiro
    ) {
        return service.autoRegistoCliente(dto.withFicheiro(ficheiro));
    }
    @PostMapping("/login")
    public ResponseEntity<Resposta> login(
            @RequestBody UtilizadorLoginRequisicaoDto dto
    ) {
        return service.login(dto);
    }

    @PostMapping(value = "/resetar-pwd")
    public ResponseEntity<Resposta> resetPassword(@RequestBody ResetSenhaRequisicaoDto request){
        return  service.passwordReset(request);
    }

}


