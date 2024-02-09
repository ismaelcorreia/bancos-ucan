package edu.ucan.sdp2.bancocore.dto.respostas;



import edu.ucan.sdp2.bancocore.entities.Endereco;
import edu.ucan.sdp2.bancocore.enums.Status;
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
    private Status status;
    private List<String> listaPerfil;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataActualizacao;
    private Endereco enderecoActual;
    private Endereco enderecoNascimento;
}
