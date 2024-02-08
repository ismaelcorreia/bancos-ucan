package edu.ucan.sdp2.bancoengenhariaservice.services;


import edu.ucan.sdp2.bancoengenhariaservice.configuration.messages.ISMSServico;
import edu.ucan.sdp2.bancoengenhariaservice.configuration.messages.SMSModelo;
import edu.ucan.sdp2.bancoengenhariaservice.configuration.security.JWTUtil;
import edu.ucan.sdp2.bancoengenhariaservice.dto.Resposta;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.ResetSenhaRequisicaoDto;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.UtilizadorAutoRegistoDto;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.UtilizadorLoginRequisicaoDto;
import edu.ucan.sdp2.bancoengenhariaservice.dto.respostas.UtilizadorLoginRespostaDto;
import edu.ucan.sdp2.bancoengenhariaservice.enums.Status;
import edu.ucan.sdp2.bancoengenhariaservice.enums.UserRole;
import edu.ucan.sdp2.bancoengenhariaservice.mapper.UtilizadorMapper;
import edu.ucan.sdp2.bancoengenhariaservice.models.TelefoneVerificacao;
import edu.ucan.sdp2.bancoengenhariaservice.models.Utilizador;
import edu.ucan.sdp2.bancoengenhariaservice.repositories.TelefoneVerificacaoRepository;
import edu.ucan.sdp2.bancoengenhariaservice.repositories.TransacaoRepository;
import edu.ucan.sdp2.bancoengenhariaservice.repositories.UtilizadorRepository;
import edu.ucan.sdp2.bancoengenhariaservice.utils.ManipiladorTempoUtil;
import edu.ucan.sdp2.bancoengenhariaservice.utils.ManipuladorFicheiroUtil;
import edu.ucan.sdp2.bancoengenhariaservice.utils.ManipuladorTextoUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.common.metrics.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class AutenticacaoService {

    private final PasswordEncoder passwordEncoder;
    private final ManipuladorFicheiroUtil manipuladorFicheiroUtil;
    private final UtilizadorRepository repository;
    private final JWTUtil jwtUtil;
    private final UtilizadorMapper mapper;
    private final AutorizacaoService autorizacaoService;

    public ResponseEntity<Resposta> passwordReset(ResetSenhaRequisicaoDto request) {
        var resposta = autorizacaoService.autorizarTelefoneVerificacao(request.getPhoneNumber(), request.getCode());

        if (!resposta.getDados()) {
            return resposta.recusado();
        }

        var usr = repository.findByEmailOrUsernameOrTelefone(
                request.getPhoneNumber(),
                request.getPhoneNumber(),
                request.getPhoneNumber());

        usr.setPalavraPasse(passwordEncoder.encode(request.getNewPassword()));
        repository.save(usr);

        return login(UtilizadorLoginRequisicaoDto.builder()
                .build()
                .withEmail(usr.getEmail())
                .withPalavraPasse(request.getNewPassword())
        );
    }
    @SneakyThrows
    public ResponseEntity<Resposta> autoRegistoCliente(UtilizadorAutoRegistoDto request) {

        if (!request.isValido()) {
            return new Resposta<String>(request.getMensagemErro(), null).recusado();
        }
        Utilizador user = mapper.deUtilizadorAutoRegisto(request);
        var usr = repository.findByEmailOrUsernameOrTelefone(user.getEmail(), user.getUsername(), user.getTelefone());
        if (usr != null) {
            return new Resposta<String>("Este usuário já existe", null).recusado();
        }

        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setPalavraPasse(passwordEncoder.encode(user.getPalavraPasse()));
        user.setRoles(List.of(UserRole.Role_Cliente));

        final String username = gerarNomeUtilizador(user.getNomeCompleto());
        user.setUsername(username);
        user.setStatus(Status.PENDING);
        var userSaved = repository.save(user);

        if (!request.getFicheiro().isEmpty()) {
            final String urlImage = manipuladorFicheiroUtil.salvarImagemPerfil(userSaved.getUsername(), request.getFicheiro());
            userSaved.setFotografia(urlImage);
        }



        return login(new UtilizadorLoginRequisicaoDto(
                user.getUsername(),
                request.getPalavraPasse()
        ));
    }

    public ResponseEntity<Resposta> login(UtilizadorLoginRequisicaoDto requisicaoDto) {

        if (!requisicaoDto.isValido()) {
            return new Resposta<String>(requisicaoDto.getMensagemErro(), null).recusado();
        }
        Utilizador utilizador = repository.findByEmailOrUsernameOrTelefone(requisicaoDto.getEmail(), requisicaoDto.getEmail(), requisicaoDto.getEmail());

        if (utilizador == null) {
            return new Resposta<String>("Não encontramos o teu perfil!", null).naoEncontrado();
        }else if (!utilizador.isEnabled()){
            return new Resposta<String>("Esta conta está desactivada por alguma razão!", null).recusado();
        }else  if(!utilizador.isAccountNonLocked()) {
            return new Resposta<String>("Esta conta está bloqueada por alguma razão!", null).recusado();
        }
        else  if(!passwordEncoder.matches(requisicaoDto.getPalavraPasse(), utilizador.getPassword())) {
            return new Resposta<String>("A palavra-passe está incorrecta!", null).recusado();
        }
        final String jwtToken = jwtUtil.gerarToken(utilizador, 20);
        return new Resposta<UtilizadorLoginRespostaDto>(
                "Bem-vindo!",
                UtilizadorLoginRespostaDto.builder()
                        .token(jwtToken)
                        .utilizador(mapper.paraUtilizadorResposta(utilizador))
                        .build()
        ).sucesso();
    }

    private String gerarNomeUtilizador(String name) {

        var splitedName = name.split(" ");
        var _username = String.format("@%s",
                splitedName[0].toLowerCase());
        Utilizador usr = repository.findByEmailOrUsernameOrTelefone(_username, _username, _username);

        if (usr != null) {
            int number =  repository.findAll().size() + new Random().nextInt(100);
            return String.format("%s%d",
                    _username,
                    (number+1)
            );
        }
        return _username;
    }





}
