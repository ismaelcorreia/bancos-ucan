package edu.ucan.sdp2.bancocore.bootstrap;

import edu.ucan.sdp2.bancocore.dto.requisicoes.EnderecoRequisicaoDto;
import edu.ucan.sdp2.bancocore.services.EnderecoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class InicializadorEnderecos implements CommandLineRunner {
    private final EnderecoService enderecoService;
    @Override
    public void run(String... args) throws Exception {
        if (enderecoService.total() == 0) {
            var listaEnderecos = Arrays.asList(
                    new EnderecoRequisicaoDto("Angola 🇦🇴, Luanda, Kilamba Bloco A, Apt 32"),
                    new EnderecoRequisicaoDto("Angola 🇦🇴, Luanda, Kilamba Bloco B, Apt 12"),
                    new EnderecoRequisicaoDto("Angola 🇦🇴, Luanda, KK5000, Apt 42"),
                    new EnderecoRequisicaoDto("Angola 🇦🇴, Luanda, Viana, Vila de Viana"),
                    new EnderecoRequisicaoDto("Angola 🇦🇴, Luanda, Viana, Luanda-Sul"),
                    new EnderecoRequisicaoDto("Angola 🇦🇴, Luanda, Belas, Talatona, Cidade Financeira, Bloco C, Apt 3")
            );
            
            for (EnderecoRequisicaoDto endereco: listaEnderecos) {
                enderecoService.criar(endereco);
            }
            log.info("Enderecos inicializado");
        }

    }
}
