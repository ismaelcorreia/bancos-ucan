package edu.ucan.sdp2.bancoengenhariaservice.services;

import edu.ucan.sdp2.bancoengenhariaservice.configuration.messages.ISMSServico;
import edu.ucan.sdp2.bancoengenhariaservice.configuration.messages.SMSModelo;
import edu.ucan.sdp2.bancoengenhariaservice.dto.Resposta;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.ContaRequisicaoDto;
import edu.ucan.sdp2.bancoengenhariaservice.enums.Status;
import edu.ucan.sdp2.bancoengenhariaservice.models.TelefoneVerificacao;
import edu.ucan.sdp2.bancoengenhariaservice.repositories.TelefoneVerificacaoRepository;
import edu.ucan.sdp2.bancoengenhariaservice.repositories.UtilizadorRepository;
import edu.ucan.sdp2.bancoengenhariaservice.utils.ManipiladorTempoUtil;
import edu.ucan.sdp2.bancoengenhariaservice.utils.ManipuladorTextoUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class AutorizacaoService {

    private final ISMSServico smsServico;
    private final TelefoneVerificacaoRepository telefoneVerificacaoRepository;


    public Resposta<Boolean> autorizarTelefoneVerificacao(String telefone, String codigo) {
        var accountVerification = telefoneVerificacaoRepository.getTelefoneVerificaoByTelefoneAndCodigo(telefone, codigo);
        if (accountVerification == null) {
            return new Resposta<>("Autorização recusada!", false);
        }
        if (accountVerification.getStatus().equals(Status.PENDING)){
            accountVerification.setStatus(Status.USED);
        }
        telefoneVerificacaoRepository.save(accountVerification);
        return new Resposta<>("Autorização concebida!", true);
    }


    // Esta função serve para requisitar a activação da conta
    public ResponseEntity<Resposta> requisitarActivacaoConta(String telefone) {

        final TelefoneVerificacao antigoTelefoneVerificacao = telefoneVerificacaoRepository.getFirstByTelefoneAndStatus(telefone, Status.ACTIVE);

//        TODO: Eliminar a existência de uma requisicao de activação anterior
        if (antigoTelefoneVerificacao != null){
            actualizarStatusTelefoneVerificacao(antigoTelefoneVerificacao, Status.DELETED);
        }

        final String codigoVerificacao = ManipuladorTextoUtil.gerarDigitosAleatorios();
        TelefoneVerificacao telefoneVerificacao = new TelefoneVerificacao();
        telefoneVerificacao.setCodigo(codigoVerificacao);
        telefoneVerificacao.setTelefone(telefone);
        telefoneVerificacao.setStatus(Status.ACTIVE);

        // Armazenar para consolidar a verificação
        telefoneVerificacaoRepository.save(telefoneVerificacao);

        // Enviar mensagem
        smsServico.enviarMensagem(
                new SMSModelo(
                        telefone,
                        String.format( "Utilize o seguinte código %s para seguir.", codigoVerificacao)
                )
        );

        // Se chegou aqui, parece que está tudo bem!
        return new Resposta<String>(String.format("Enviamos um código de verificação de conta para {}", telefone), null).sucesso();
    }


    public  ResponseEntity<Resposta> verificacaoCodigoTelefone(String telefone, String codigo) {


        final TelefoneVerificacao telefoneVerificacao = telefoneVerificacaoRepository.getTelefoneVerificaoByTelefoneAndCodigo(telefone, codigo);
        if (telefoneVerificacao != null) {
            if (telefoneVerificacao.getStatus().equals(Status.ACTIVE)) {
                var dataCriacao = telefoneVerificacao.getDataCriacao();
                if (!ManipiladorTempoUtil.isTempoMaior(dataCriacao, LocalDateTime.now())) {
                    actualizarStatusTelefoneVerificacao(telefoneVerificacao, Status.USED);
                    return new Resposta<String>("Código confirmado com sucesso", null).sucesso();
                }
                actualizarStatusTelefoneVerificacao(telefoneVerificacao, Status.EXPIRED);
                return new Resposta<String>("O tempo expirou! por favor, volte para gerar um novo código", null).recusado();
            }
            return new Resposta<String>("Este código já foi usado, tente gerar novamente", null).recusado();
        }
        return new Resposta<String>("Este código não existe!", null).naoEncontrado();
    }

    void actualizarStatusTelefoneVerificacao(TelefoneVerificacao telefoneVerificacao, Status status) {
        telefoneVerificacao.setStatus(status);
        telefoneVerificacaoRepository.save(telefoneVerificacao);
    }
}
