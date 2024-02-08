package edu.ucan.sdp2.bancoengenhariaservice.controllers.publico;
import edu.ucan.sdp2.bancoengenhariaservice.dto.Resposta;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.EnderecoRequisicaoDto;
import edu.ucan.sdp2.bancoengenhariaservice.services.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publico")
@RequiredArgsConstructor
public class ControladorPublico {

    private final EnderecoService enderecoService;

    @PostMapping("/enderecos")
    public ResponseEntity<Resposta> criarEndereco(@RequestBody EnderecoRequisicaoDto endereco){
        return enderecoService.criar(endereco);
    }
    @GetMapping("/enderecos")
    public ResponseEntity<Resposta> listarEnderecos(){
        return enderecoService.listar();
    }
}
