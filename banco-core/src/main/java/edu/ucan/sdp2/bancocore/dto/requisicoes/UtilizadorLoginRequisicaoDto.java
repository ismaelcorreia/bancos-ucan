package edu.ucan.sdp2.bancocore.dto.requisicoes;


import edu.ucan.sdp2.bancocore.entities.Utilizador;
import lombok.*;



@Getter
@Setter
@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UtilizadorLoginRequisicaoDto extends EntidadeRequisicaoAbstract<Utilizador> {

    private String email;
    private String palavraPasse;


    @Override
    public Utilizador mapearEntidade() {
        Utilizador utilizador = new Utilizador();
        utilizador.setEmail(email);
        utilizador.setPalavraPasse(palavraPasse);
        return utilizador;
    }

    @Override
    public boolean isValido() {
        if (email == null) {
            this.mensagemErro ="O e-mail é um campo obrigatório!";
            return false;
        }else if (palavraPasse == null) {
            this.mensagemErro ="A palavra-passe é um campo obrigatório!";
            return false;
        }
        return true;
    }
}
