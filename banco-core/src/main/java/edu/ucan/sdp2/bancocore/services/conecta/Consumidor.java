package edu.ucan.sdp2.bancocore.services.conecta;

import edu.ucan.sdp2.bancocore.entities.ContaBancaria;
import edu.ucan.sdp2.bancocore.entities.Movimento;
import edu.ucan.sdp2.bancocore.enums.Status;
import edu.ucan.sdp2.bancocore.mapper.MovimentoMapper;
import edu.ucan.sdp2.bancocore.repositories.ContaBancariaRepository;
import edu.ucan.sdp2.bancocore.services.ContaService;
import edu.ucan.sdp2.bancocore.services.MovimentoService;
import edu.ucan.sdp2.bancocore.services.TransacaoService;
import edu.ucan.sdp2.conectacore.enums.TipoEvento;
import edu.ucan.sdp2.conectacore.listener.OuvinteAbstract;
import edu.ucan.sdp2.conectacore.models.TransacaoConecta;
import edu.ucan.sdp2.conectacore.models.TransacaoConectaEncriptado;
import edu.ucan.sdp2.conectacore.service.ConectaEmissorService;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Slf4j
@Component
public class Consumidor extends OuvinteAbstract
{




    @Value("${banco.identificador.chave}")
    private String chave;
    private final MovimentoService movimentoService;
    private final TransacaoService transacaoService;
    private final ContaService contaService;
    private final ConectaEmissorService conectaEmissorService;

    @Autowired
    public Consumidor(MovimentoService movimentoService, TransacaoService transacaoService, ContaService contaService, ConectaEmissorService conectaEmissorService) {
        this.movimentoService = movimentoService;
        this.transacaoService = transacaoService;
        this.contaService = contaService;
        this.conectaEmissorService = conectaEmissorService;
    }


    private Movimento getMovimentoFromJson(String json) {
        log.info("Mensagem recebida Em Falha: {}", json);
        var transacaoEncriptado = TransacaoConectaEncriptado.fromJson(json);
        TransacaoConecta transacao = TransacaoConectaEncriptado.toTransacao(transacaoEncriptado, chave);
        log.info("Movimento: {}", transacao.toJson());
        return MovimentoMapper.transacaoConectaDetalhesParaMovimentos(transacao.getDetalhes());

    }

    @SneakyThrows
    @Override
    public void escuta(Map<TipoEvento, String> data) {

    }

    @Override
    @Transactional
    public void requisicaoTransferencia(String data) {
            log.info("REQUISICAO TRANSFERENCIA");
        var transacaoEncriptada =  TransacaoConectaEncriptado.fromJson(data);
        try{
            TransacaoConecta transacao = TransacaoConectaEncriptado.toTransacao(transacaoEncriptada, chave);
           var contaBancaria = contaService.findFirstByIbanConta(transacao.getDetalhes().getIbanDestino());
            if(contaBancaria == null
            || contaBancaria.getStatus().equals(Status.DEACTIVE)){
                conectaEmissorService.enviarMovimentoToConnecta(transacaoEncriptada, TipoEvento.ROLLBACK_TRANSFERENCIA.name());
            }

            movimentoService.creditarConta(contaBancaria, transacao.getDetalhes().getValor());
            conectaEmissorService.enviarMovimentoToConnecta(transacaoEncriptada, TipoEvento.TRANSFERENCIA_REALIZADA.name());
        }
        catch (Exception exception) {
            log.error(exception.getMessage());
            conectaEmissorService.enviarMovimentoToConnecta(transacaoEncriptada, TipoEvento.ROLLBACK_TRANSFERENCIA.name());
        }

    }

    @Override
    public void requisicaoCredito(String data) {

    }

    @Override
    public void aprovacaoCredito(String data) {

    }

    @Override
    public void realizacaoCredito(String data) {

    }

    @Override
    public void rollbackTransferencia(String data) {
        log.info("ROLLBACK TRANSFERÊNCIA");
        var transacaoEncriptada =  TransacaoConectaEncriptado.fromJson(data);
        var transacao = transacaoService.getTransacaoRepository().findById(UUID.fromString(transacaoEncriptada.getNumeroTransacao())).orElse(null);
        if (transacao != null) {
            movimentoService.reverterMovimento(transacao.getMovimento());
        }
    }

    @Override
    public void transferenciaRealizada(String data) {
        log.info("TRANSFERÊNCIA REALIZADA");
        var transacaoEncriptada = TransacaoConectaEncriptado.fromJson(data);
        var transacao = transacaoService.getTransacaoRepository().findById(UUID.fromString(transacaoEncriptada.getNumeroTransacao())).orElse(null);
        if (transacao != null) {
            movimentoService.actualizarSaldoContabilistico(transacao.getMovimento());
        }
    }


}
