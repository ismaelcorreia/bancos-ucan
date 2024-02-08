package edu.ucan.sdp2.bancoengenhariaservice.controllers.publico;

import edu.ucan.sdp2.bancoengenhariaservice.dto.Resposta;
import edu.ucan.sdp2.bancoengenhariaservice.services.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/publico")
@RequiredArgsConstructor
public class ControladorPublico {

    private final EnderecoService enderecoService;

//    @PostMapping("/endereco")
//    public ResponseEntity<Resposta> criarEndereco(@RequestBody String endereco){
//
//    }
}
