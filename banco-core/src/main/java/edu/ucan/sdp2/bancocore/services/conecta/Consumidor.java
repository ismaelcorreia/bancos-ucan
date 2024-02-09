package edu.ucan.sdp2.bancocore.services.conecta;

import edu.ucan.sdp2.bancocore.entities.Movimento;
import edu.ucan.sdp2.bancocore.mapper.MovimentoMapper;
import edu.ucan.sdp2.bancocore.services.MovimentoService;
import edu.ucan.sdp2.conectacore.listener.OuvinteAbstract;
import edu.ucan.sdp2.conectacore.models.TransacaoConecta;
import edu.ucan.sdp2.conectacore.models.TransacaoConectaEncriptado;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



@Slf4j
@Component
public class Consumidor extends OuvinteAbstract
{




    @Value("${banco.identificador.chave}")
    private String chave;
    private final MovimentoService movimentoService;

    @Autowired
    public Consumidor(MovimentoService movimentoService) {
        this.movimentoService = movimentoService;
    }

    @SneakyThrows
    @Override
    public void escuta(String json) {
        log.info("Mensagem recebida Em Falha: {}", json);
        log.info("PREPARANDO O ROLL-BACK: ");
        var movimento = getMovimentoFromJson(json);
        movimentoService.movimentarContaPorIban(movimento);
    }

    @Override
    public void falha(String json) {
        log.info("Mensagem recebida Em Falha: {}", json);
        log.info("PREPARANDO O ROLL-BACK: ");
        var movimento = getMovimentoFromJson(json);
        movimentoService.reverterMovimento(movimento);
    }


    private Movimento getMovimentoFromJson(String json) {
        log.info("Mensagem recebida Em Falha: {}", json);
        var transacaoEncriptado = TransacaoConectaEncriptado.fromJson(json);
        TransacaoConecta transacao = TransacaoConectaEncriptado.toTransacao(transacaoEncriptado, chave);
        log.info("Movimento: {}", transacao.toJson());
        return MovimentoMapper.transacaoConectaDetalhesParaMovimentos(transacao.getDetalhes());

    }
}
