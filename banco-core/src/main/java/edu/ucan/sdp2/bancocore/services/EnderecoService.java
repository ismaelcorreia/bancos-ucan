package edu.ucan.sdp2.bancocore.services;

import edu.ucan.sdp2.bancocore.dto.Resposta;
import edu.ucan.sdp2.bancocore.dto.requisicoes.EnderecoRequisicaoDto;
import edu.ucan.sdp2.bancocore.dto.requisicoes.EntidadeRequisicaoAbstract;
import edu.ucan.sdp2.bancocore.entities.Endereco;
import edu.ucan.sdp2.bancocore.repositories.EnderecoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class EnderecoService extends ServicoGenerico<Endereco, Endereco>{

    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository){
        this.repository = enderecoRepository;
    }

    @Override
    protected Endereco mapearResposta(Endereco model) {
        return model;
    }
    @Override
    public ResponseEntity<Resposta> criar(EntidadeRequisicaoAbstract<Endereco> entityRequest) {
        var reuisicaoDto = (EnderecoRequisicaoDto)entityRequest;

        var listaEnderecos = reuisicaoDto.getEndereco().split(",");
        Endereco enderecoPai = null;
        for(String e: listaEnderecos) {
            var endereco = ((EnderecoRepository) repository).getFirstByNome(e.trim());
            if (endereco == null) {
                endereco = new Endereco(e.trim(), enderecoPai);
                enderecoPai = repository.save(endereco);
            }else {
                enderecoPai = endereco;
            }
        }
        return new Resposta<Endereco>("O endereço foi salvo com sucesso", enderecoPai).sucesso();
    }

    @Override
    public ResponseEntity<Resposta> actualizar(UUID id, EntidadeRequisicaoAbstract<Endereco> entityRequest) {

        return null;
    }


    public ResponseEntity<Resposta> actualizarNome(UUID id, String nome) {

        var endereco = repository.findById(id).orElse(null);
        if (endereco == null){
            return new Resposta("Lamentamos mas não encontramos um endereço com este ID", null).naoEncontrado();
        }
        endereco.setNome(nome);
        return new Resposta("O endereço foi salvo com sucesso",  repository.save(endereco)).sucesso();
    }

    @Override
    public ResponseEntity<Resposta> deletar(UUID id) {
        return super.deletarPadrao(id);
    }
}
