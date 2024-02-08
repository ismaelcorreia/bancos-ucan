package edu.ucan.sdp2.bancoengenhariaservice.dto.respostas;


import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.EntidadeRequisicaoAbstract;
import edu.ucan.sdp2.bancoengenhariaservice.enums.Status;
import edu.ucan.sdp2.bancoengenhariaservice.models.Endereco;
import edu.ucan.sdp2.bancoengenhariaservice.models.Utilizador;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilizadorRespostaDto{
    private UUID id;
    private String nomeUtilizador;
    private String nomeCompleto;
    private String email;
    private String telefone;
    private String bilheteIdentidade;
    private String fotografia;
    private LocalDate dataNascimento;
    private Endereco enderecoActual;
    private Endereco enderecoNascimento;
    private Status status;
    private List<String> listaPerfil;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataActualizacao;
}
