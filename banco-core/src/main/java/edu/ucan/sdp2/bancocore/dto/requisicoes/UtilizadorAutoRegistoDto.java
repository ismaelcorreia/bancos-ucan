package edu.ucan.sdp2.bancocore.dto.requisicoes;


import edu.ucan.sdp2.bancocore.entities.Utilizador;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.With;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@With
public class UtilizadorAutoRegistoDto  extends EntidadeRequisicaoAbstract<Utilizador> {



    private String email;
    private String telefone;
    private String palavraPasse;
    private String nomeCompleto;
    private String numeroBilhete;
    private LocalDate dataNascimento;
    private MultipartFile ficheiro;


    @Override
    public Utilizador mapearEntidade() {
        Utilizador utilizador = new Utilizador();
        utilizador.setEmail(this.email);
        utilizador.setTelefone(this.telefone);
        utilizador.setPalavraPasse(this.palavraPasse);
        utilizador.setNomeCompleto(this.nomeCompleto);
        utilizador.setNumeroBilhete(this.numeroBilhete);
        return utilizador;
    }

    @Override
    public boolean isValido() {
        if (nomeCompleto == null || nomeCompleto.isBlank()){
            this.mensagemErro = "O nome é um campo obrigatório, deve estar preenchido correctamente";
            return false;
        }else if(telefone == null || telefone.isBlank()){
            this.mensagemErro = "O telefone é um campo obrigatório, deve estar preenchido correctamente";
            return false;
        }else if(numeroBilhete == null || numeroBilhete.isBlank()){
            this.mensagemErro = "O bilhete é um campo obrigatório, deve estar preenchido correctamente";
            return false;
        }else if(palavraPasse == null || palavraPasse.isBlank()){
            this.mensagemErro = "A palavra-passe é um campo obrigatório!";
            return false;
        }
        return true;
    }
}
