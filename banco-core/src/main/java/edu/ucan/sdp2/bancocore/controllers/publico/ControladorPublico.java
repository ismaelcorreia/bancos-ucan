package edu.ucan.sdp2.bancocore.controllers.publico;
import edu.ucan.sdp2.bancocore.dto.Resposta;
import edu.ucan.sdp2.bancocore.dto.requisicoes.EnderecoRequisicaoDto;
import edu.ucan.sdp2.bancocore.services.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
