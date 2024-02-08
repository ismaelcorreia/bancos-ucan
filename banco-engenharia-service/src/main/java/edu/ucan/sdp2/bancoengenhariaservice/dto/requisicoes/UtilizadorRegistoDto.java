package edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes;


import edu.ucan.sdp2.bancoengenhariaservice.enums.UserRole;
import edu.ucan.sdp2.bancoengenhariaservice.models.Endereco;
import edu.ucan.sdp2.bancoengenhariaservice.models.Utilizador;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.With;
import org.springframework.web.multipart.MultipartFile;

import javax.management.relation.Role;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@With
public class UtilizadorRegistoDto extends EntidadeRequisicaoAbstract<Utilizador> {



    private String username;
    private String email;
    private String telefone;
    private String palavraPasse;
    private String nomeCompleto;
    private String numeroBilhete;
    private LocalDate dataNascimento;
    private Endereco enderecoActual;
    private Endereco enderecoNascimento;
    private MultipartFile image;
    private List<UserRole> perfies;




    @Override
    public Utilizador mapearEntidade() {
        Utilizador utilizador = new Utilizador();
        utilizador.setUsername(this.username);
        utilizador.setEmail(this.email);
        utilizador.setTelefone(this.telefone);
        utilizador.setPalavraPasse(this.palavraPasse);
        utilizador.setNomeCompleto(this.nomeCompleto);
        utilizador.setNumeroBilhete(this.numeroBilhete);
        utilizador.setEnderecoActual(this.enderecoActual);
        utilizador.setEnderecoNascimento(this.enderecoNascimento);
        utilizador.setRoles(this.perfies);
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
        }else if(perfies == null || perfies.isEmpty()){
            this.mensagemErro = "Deve ter pelo menos 1 perfil";
            return false;
        }
        return true;
    }
}
