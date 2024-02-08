package edu.ucan.sdp2.bancoengenhariaservice.services;
import edu.ucan.sdp2.bancoengenhariaservice.dto.Resposta;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.ContaRequisicaoDto;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.EntidadeRequisicaoAbstract;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.UtilizadorFotografiaRequisicaoDto;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.UtilizadorRegistoDto;
import edu.ucan.sdp2.bancoengenhariaservice.dto.respostas.UtilizadorRespostaDto;
import edu.ucan.sdp2.bancoengenhariaservice.enums.Status;
import edu.ucan.sdp2.bancoengenhariaservice.enums.UserRole;
import edu.ucan.sdp2.bancoengenhariaservice.models.Utilizador;
import edu.ucan.sdp2.bancoengenhariaservice.repositories.UtilizadorRepository;
import edu.ucan.sdp2.bancoengenhariaservice.utils.ManipuladorFicheiroUtil;
import edu.ucan.sdp2.bancoengenhariaservice.utils.SessaoRequisicao;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UtilizadorService extends ServicoGenerico<Utilizador, UtilizadorRespostaDto> implements UserDetailsService{


    private final ManipuladorFicheiroUtil manipuladorFicheiroUtil;
    private final ContaService contaService;
    private final AutorizacaoService autorizacaoService;
    @Autowired
    public UtilizadorService(UtilizadorRepository repository, ManipuladorFicheiroUtil manipuladorFicheiroUtil, ContaService contaService, AutorizacaoService autorizacaoService) {
        this.manipuladorFicheiroUtil = manipuladorFicheiroUtil;
        this.contaService = contaService;
        this.autorizacaoService = autorizacaoService;
        super.repository = repository;
    }
    @Override
    protected UtilizadorRespostaDto mapearResposta(Utilizador utilizador) {
        return UtilizadorRespostaDto.builder()
                .id(utilizador.getId())
                .nomeCompleto(utilizador.getNomeCompleto())

                .email(utilizador.getEmail())
                .listaPerfil(utilizador.getRoles().stream().map(UserRole::getDescricao).collect(Collectors.toList()))
                .bilheteIdentidade(utilizador.getNumeroBilhete())
                .status(utilizador.getStatus())
                .dataNascimento(utilizador.getDataNascimento())
                .enderecoActual(utilizador.getEnderecoActual())
                .enderecoNascimento(utilizador.getEnderecoNascimento())
                .fotografia(utilizador.getFotografia())
                .nomeUtilizador(utilizador.getUsername())
                .telefone(utilizador.getTelefone())
                .dataCriacao(utilizador.getDataCriacao())
                .dataActualizacao(utilizador.getDataActualizacao())
                .build();
    }
    @Override
    public ResponseEntity<Resposta> criar(EntidadeRequisicaoAbstract<Utilizador> entidadeRequisicao) {
        return super.criarPadrao(entidadeRequisicao);
    }
    @SneakyThrows
    public ResponseEntity<Resposta> registoUtilizador(UtilizadorRegistoDto request) {
        if (!request.isValido()) {
            return new Resposta<String>(request.getMensagemErro(), null).recusado();
        }
        Utilizador user = request.mapearEntidade();
        var usr = getUtilizadorRepository().findByEmailOrUsernameOrTelefone(user.getEmail(), user.getUsername(), user.getTelefone());
        if (usr != null) {
            return new Resposta<String>("Este usuário já existe", null).recusado();
        }

        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
//        user.setPalavraPasse(passwordEncoder.encode(user.getPalavraPasse()));
        if (!request.getImage().isEmpty()) {
            final String urlImage = manipuladorFicheiroUtil.salvarImagemPerfil(user.getUsername(), request.getImage());
            user.setFotografia(urlImage);
        }

        return  new Resposta<UtilizadorRespostaDto>("Utilizador cadastrado com sucesso!",  mapearResposta(repository.save(user))).sucesso();
    }



    public ResponseEntity<Resposta> actualizarFotografia(UtilizadorFotografiaRequisicaoDto registoDto) {
        if (!registoDto.isValido()) {
            return new Resposta<String>(registoDto.getMensagemErro(), null).recusado();
        }
        Utilizador user = registoDto.mapearEntidade();
        var usr = getUtilizadorRepository().findById(user.getId());
        if (usr == null) {
            return new Resposta<String>("Não encontramos o seu perfil", null).naoEncontrado();
        }
        manipuladorFicheiroUtil.replace(user.getFotografia(), registoDto.getFicheiro());
        return new Resposta<String>("Fotografia de perfil alterado com sucesso", null).sucesso();
    }


    public ResponseEntity<Resposta> activarMinhaConta(String telefone, String codigo) {



        var utilizador = getUtilizadorRepository().findByEmailOrUsernameOrTelefone(
                telefone,
                telefone,
                telefone
        );

        if (utilizador == null) {
            return new Resposta<>("Utilizador inexistente", null).naoEncontrado();
        }

        var response = autorizacaoService.verificacaoCodigoTelefone(telefone, codigo);

       if (Objects.requireNonNull(response.getBody()).getCodigo() != 200) {
           return response;
       }
        utilizador.setStatus(Status.ACTIVE);
        getUtilizadorRepository().save(utilizador);
        ContaRequisicaoDto contaRequisicaoDto = new ContaRequisicaoDto(
                0,
                List.of(SessaoRequisicao.utilizador.getId().toString())
        );

        return contaService.criar(contaRequisicaoDto);
    }
    @Override
    public ResponseEntity<Resposta> actualizar(UUID id, EntidadeRequisicaoAbstract<Utilizador> entidadeRequisicao) {
        return super.actualizarPadrao(id, entidadeRequisicao);
    }

    @Override
    public ResponseEntity<Resposta> deletar(UUID id) {
        return super.deletarPadrao(id);
    }

    private UtilizadorRepository getUtilizadorRepository(){
       return ((UtilizadorRepository)repository);
    }

    @Override
    public Utilizador loadUserByUsername(String login) {
        return  Optional
                .of(getUtilizadorRepository().findByEmailOrUsernameOrTelefone(
                        login,
                        login,
                        login
                ))
                .orElseThrow(()-> new UsernameNotFoundException("Utilizador não encontrado"));
    }



}
