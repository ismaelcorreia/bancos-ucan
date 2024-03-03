package edu.ucan.sdp2.bancocore.services;

import edu.ucan.sdp2.bancocore.dto.Resposta;
import edu.ucan.sdp2.bancocore.dto.requisicoes.ContaRequisicaoDto;
import edu.ucan.sdp2.bancocore.dto.requisicoes.EntidadeRequisicaoAbstract;
import edu.ucan.sdp2.bancocore.dto.respostas.ContaRespotaDto;
import edu.ucan.sdp2.bancocore.entities.ContaBancaria;
import edu.ucan.sdp2.bancocore.entities.Utilizador;
import edu.ucan.sdp2.bancocore.repositories.ContaBancariaRepository;
import edu.ucan.sdp2.bancocore.repositories.UtilizadorRepository;
import edu.ucan.sdp2.bancocore.utils.ManipuladorContaUtil;
import edu.ucan.sdp2.bancocore.utils.SessaoRequisicao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContaService extends ServicoGenerico<ContaBancaria, ContaRespotaDto>{

    private final ManipuladorContaUtil manipuladorContaUtil;
    private final UtilizadorRepository utilizadorRepository;
    @Autowired
    public ContaService(ContaBancariaRepository contaBancariaRepository, ManipuladorContaUtil manipuladorContaUtil, UtilizadorRepository utilizadorRepository){
        this.manipuladorContaUtil = manipuladorContaUtil;
        this.utilizadorRepository = utilizadorRepository;
        this.repository = contaBancariaRepository;
    }

    @Override
    protected ContaRespotaDto mapearResposta(ContaBancaria model) {

        ContaRespotaDto contaRespotaDto = new ContaRespotaDto();
        contaRespotaDto.setId(model.getId());
        contaRespotaDto.setNumero(model.getNumeroConta());
        contaRespotaDto.setIban(model.getIbanConta());
        contaRespotaDto.setSaldoContabilistico(model.getSaldoContabilistico());
        contaRespotaDto.setSaldoDisponivel(model.getSaldoDisponivel());
        contaRespotaDto.setUltimaActualizacao(model.getDataActualizacao());
        return contaRespotaDto;
    }
    @Override
    public ResponseEntity<Resposta> criar(EntidadeRequisicaoAbstract<ContaBancaria> entityRequest) {
        var contaRequisicao = (ContaRequisicaoDto) entityRequest;
        if (!contaRequisicao.isValido()){
            return new Resposta<>(contaRequisicao.getMensagemErro(), null).recusado();
        }
        var conta = contaRequisicao.mapearEntidade();
        final long proximoNumero = repository.count() + 1;
        List<Utilizador> representantesVerificados = new ArrayList<Utilizador>();
        for (Utilizador reprentante : conta.getRepresentantes()) {
            var rep = utilizadorRepository.findById(reprentante.getId()).orElse(null);
            if (rep == null) {
                return new Resposta<>("O representate identificado pelo ID "+reprentante.getId().toString()+" não existe no sistema!", null).recusado();
            }
            representantesVerificados.add(rep);
        }

        conta.setRepresentantes(representantesVerificados);
        conta.setNumeroConta(manipuladorContaUtil.formatarNumeroConta(proximoNumero));
        conta.setIbanConta(manipuladorContaUtil.formatarIBANConta(proximoNumero));
        return new Resposta<>("Conta criada com sucesso", repository.save(conta)).sucesso();
    }

    @Override
    public ResponseEntity<Resposta> actualizar(UUID id, EntidadeRequisicaoAbstract<ContaBancaria> entityRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Resposta> deletar(UUID id) {
        return super.deletarPadrao(id);
    }



    public ResponseEntity<Resposta> contaPorIban(String iban) {
        var conta = getContaBancariaRepository().findFirstByIbanConta(iban);

        if (conta == null) {
            return new Resposta<>("Não consegimos identificar a tua conta", null).naoEncontrado();
        }
        return new Resposta<>("Conta carregada com sucesso!", conta).sucesso();
    }

    public ResponseEntity<Resposta> contaPorNumeroConta(String numero) {
        var conta = getContaBancariaRepository().findFirstByNumeroConta(numero);

        if (conta == null) {
            return new Resposta<>("Não consegimos identificar a tua conta", null).naoEncontrado();
        }
        return new Resposta<>("Conta carregada com sucesso!", conta).sucesso();
    }



    public ResponseEntity<Resposta> minhasContas() {
        var contas = getContaBancariaRepository().findByRepresentantesIn(List.of(SessaoRequisicao.utilizador));
        if (contas == null) {
            return new Resposta<>("Não há contas bancárias para si no momento", null).naoEncontrado();
        }
        return new Resposta<>("Conta carregada com sucesso!",
                contas.stream().map(this::mapearResposta).collect(Collectors.toList())).sucesso();
    }

    private ContaBancariaRepository getContaBancariaRepository() {
        return (ContaBancariaRepository)this.repository;
    }

    public ContaBancaria findFirstByIbanConta(String iban) {
        return getContaBancariaRepository().findFirstByIbanConta(iban);
    }
}
