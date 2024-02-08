package edu.ucan.sdp2.bancoengenhariaservice.controllers;

import edu.ucan.sdp2.bancoengenhariaservice.dto.Resposta;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.ResetSenhaRequisicaoDto;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.UtilizadorAutoRegistoDto;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.UtilizadorLoginRequisicaoDto;
import edu.ucan.sdp2.bancoengenhariaservice.dto.respostas.UtilizadorRespostaDto;
import edu.ucan.sdp2.bancoengenhariaservice.models.TelefoneVerificacao;
import edu.ucan.sdp2.bancoengenhariaservice.models.Utilizador;
import edu.ucan.sdp2.bancoengenhariaservice.services.AutenticacaoService;
import edu.ucan.sdp2.bancoengenhariaservice.services.AutorizacaoService;
import edu.ucan.sdp2.bancoengenhariaservice.services.UtilizadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.AbstractController;


@RestController
@RequestMapping("/utilizador")
@RequiredArgsConstructor
public class UtilizadorController extends ControladorGenerico<Utilizador, UtilizadorRespostaDto> {

    @Autowired
    public UtilizadorController(UtilizadorService utilizadorService) { this.serviceApi = utilizadorService;}



}


