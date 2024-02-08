package edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes;


import edu.ucan.sdp2.bancoengenhariaservice.models.Utilizador;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.With;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
@Setter
@Builder
@With
public class UtilizadorFotografiaRequisicaoDto extends EntidadeRequisicaoAbstract<Utilizador> {



    private String id;
    private MultipartFile ficheiro;

    @Override
    public Utilizador mapearEntidade() {
        Utilizador utilizador = new Utilizador();
        utilizador.setId(UUID.fromString(id));
        return utilizador;
    }

    @Override
    public boolean isValido() {
        if (ficheiro == null){
            this.mensagemErro = "O ficheiro da fotografia está em falta!";
            return false;
        }
        return true;
    }
}
