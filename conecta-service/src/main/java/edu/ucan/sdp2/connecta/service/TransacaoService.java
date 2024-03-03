package edu.ucan.sdp2.connecta.service;

import edu.ucan.sdp2.conectacore.enums.StatusTransacao;
import edu.ucan.sdp2.conectacore.enums.TipoEvento;
import edu.ucan.sdp2.conectacore.listener.OuvinteAbstract;
import edu.ucan.sdp2.conectacore.models.TransacaoConecta;
import edu.ucan.sdp2.conectacore.models.TransacaoConectaDetalhes;
import edu.ucan.sdp2.conectacore.models.TransacaoConectaEncriptado;
import edu.ucan.sdp2.conectacore.models.TransacaoRequisicao;
import edu.ucan.sdp2.conectacore.service.ConectaEmissorService;
import edu.ucan.sdp2.conectacore.utils.BancoUtil;
import edu.ucan.sdp2.connecta.dto.transacao.TransacaoStatusResposta;
import edu.ucan.sdp2.connecta.mappers.TransacaoMapper;
import edu.ucan.sdp2.connecta.model.Banco;
import edu.ucan.sdp2.connecta.model.Transacao;
import edu.ucan.sdp2.connecta.repo.BancoRepo;
import edu.ucan.sdp2.connecta.repo.TransacaoRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransacaoService extends OuvinteAbstract {


    private final BancoRepo bancoRepo;
    private final TransacaoMapper mapper;
    private final ConectaEmissorService emissorService;

    public Mono<ResponseEntity<TransacaoStatusResposta>> requisitar(
            UUID id,
            String chave,
            TransacaoRequisicao requisicao) {

        return  bancoRepo.findById(id).flatMap(banco -> {
                             if (banco == null) {
                                throw new IllegalArgumentException("Banco não encontrado");
                            }
                            if (!banco.getChave().equalsIgnoreCase(chave)) {
                                throw new IllegalArgumentException("Chave incorrecta!");
                            }

                            Transacao transacao = mapper.transacaoFromRequisicao(requisicao);
                            transacao.setId(UUID.randomUUID());
                            if (transacao.getNumero() == null){
                                transacao.setNumero(LocalDateTime.now().format(DateTimeFormatter.ofPattern("SSMMddyyyyHHmmss")));
                            }
                            transacao.setBancoId(banco.getId().toString());
                            var data = LocalDateTime.now();
                            transacao.setDataCriacao(data);
                            transacao.setStatus(StatusTransacao.Pendente.name());
                            transacao.setDataUltimaModificacao(data);



                            var codigoIBAN = BancoUtil.extrairIBAN(transacao.getDetalhes().getReceptor().getIban());
                            return bancoRepo.findByCodigoIBAN(codigoIBAN).flatMap(
                                    bancoDestino -> {

                                        if (bancoDestino == null) {
                                            return Mono.error(new Throwable("Não foi possível reconhecer a conta de destino!"));
                                        }

                                        if (!transacaoToDiscovery(transacao, banco.getCanal(), bancoDestino, TipoEvento.REQUISICAO_TRANSFERENCIA)) {
                                            return Mono.error(new Throwable("Não foi possível ligar-se ao serviço de consiliação"));
                                        }

                                        return Mono.just(ResponseEntity.ok(mapper.statusRespostaFromTransacao(transacao)));

                                    }
                            );

                        });
    }

    private boolean transacaoToDiscovery(Transacao transacao, String canal, Banco banco, TipoEvento evento) {

        TransacaoConecta transacaoConecta = new TransacaoConecta();
        transacaoConecta.setNumero(transacao.getNumero());
        transacaoConecta.setTopico(canal);
        transacaoConecta.setTipoEvento(evento);

        TransacaoConectaDetalhes transacaoConectaDetalhes = new TransacaoConectaDetalhes();
        transacaoConectaDetalhes.setValor(transacao.getDetalhes().getMontante());
        transacaoConectaDetalhes.setIbanOrigem(transacao.getDetalhes().getEmissor().getIban());
        transacaoConectaDetalhes.setIbanDestino(transacao.getDetalhes().getReceptor().getIban());
        transacaoConectaDetalhes.setTipoOperacao(transacao.getDetalhes().getTipoOperacao());
        transacaoConecta.setDetalhes(transacaoConectaDetalhes);
        return emissorService.enviarTransacao(transacaoConecta, banco.getChave(),  banco.getCanal());
    }


    @Override
    public void escuta(Map<TipoEvento, String> data) {
//        log.info(data.toString());
    }

    @Override
    public void requisicaoTransferencia(String data) {

    }

    @Override
    public void requisicaoCredito(String data) {
        log.info(data);
    }

    @Override
    public void aprovacaoCredito(String data) {
        log.info(data);
    }

    @Override
    public void realizacaoCredito(String data) {
        log.info(data);
    }

    @Override
    public void rollbackTransferencia(String data) {
        log.info("CONECTAR TRANSFERÊNCIA ROLLBACK...");
        var transacaoEncriptada = TransacaoConectaEncriptado.fromJson(data);
        emissorService.enviarMovimento(transacaoEncriptada, transacaoEncriptada.getTopico(), TipoEvento.ROLLBACK_TRANSFERENCIA.name());
    }

    @Override
    public void transferenciaRealizada(String data) {
        log.info("CONECTAR TRANSFERÊNCIA REALIZADA...");
        var transacaoEncriptada = TransacaoConectaEncriptado.fromJson(data);
        emissorService.enviarMovimento(transacaoEncriptada, transacaoEncriptada.getTopico(), TipoEvento.TRANSFERENCIA_REALIZADA.name());
    }



}
