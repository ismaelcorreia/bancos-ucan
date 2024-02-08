package edu.ucan.sdp2.bancoengenhariaservice.mapper;

import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.UtilizadorAutoRegistoDto;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.UtilizadorRegistoDto;
import edu.ucan.sdp2.bancoengenhariaservice.dto.respostas.UtilizadorRespostaDto;
import edu.ucan.sdp2.bancoengenhariaservice.enums.UserRole;
import edu.ucan.sdp2.bancoengenhariaservice.models.Utilizador;
import edu.ucan.sdp2.bancoengenhariaservice.utils.ManipuladorFicheiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class UtilizadorMapper {

    private final ManipuladorFicheiroUtil manipuladorFicheiroUtil;
    @Autowired
    public UtilizadorMapper(ManipuladorFicheiroUtil manipuladorFicheiroUtil){
        this.manipuladorFicheiroUtil  = manipuladorFicheiroUtil;
    }
    public Utilizador deUtilizadorAutoRegisto(UtilizadorAutoRegistoDto dto) {
        return dto.mapearEntidade();
    }
    public Utilizador deUtilizadorRegisto(UtilizadorRegistoDto dto) {
        return dto.mapearEntidade();
    }

    public UtilizadorRespostaDto paraUtilizadorResposta(Utilizador utilizador) {
        return UtilizadorRespostaDto
                .builder()
                .id(utilizador.getId())
                .enderecoNascimento(utilizador.getEnderecoNascimento())
                .nomeUtilizador(utilizador.getUsername())
                .nomeCompleto(utilizador.getNomeCompleto())
                .email(utilizador.getEmail())
                .telefone(utilizador.getTelefone())
                .fotografia(manipuladorFicheiroUtil.getUrlFileFromCurrentContext(utilizador.getFotografia()))
                .dataNascimento(utilizador.getDataNascimento())
                .enderecoActual(utilizador.getEnderecoActual())
                .listaPerfil(utilizador.getRoles().stream().map(UserRole::getDescricao).collect(Collectors.toList()))
                .status(utilizador.getStatus())
                .build();
    }

}
