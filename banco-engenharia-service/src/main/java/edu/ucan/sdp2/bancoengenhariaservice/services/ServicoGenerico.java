package edu.ucan.sdp2.bancoengenhariaservice.services;


import edu.ucan.sdp2.bancoengenhariaservice.dto.Resposta;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.EntidadeRequisicaoAbstract;
import edu.ucan.sdp2.bancoengenhariaservice.enums.Status;
import edu.ucan.sdp2.bancoengenhariaservice.models.generics.GenericId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public abstract  class ServicoGenerico<T, R>{
    protected JpaRepository<T, UUID> repository;


    protected abstract R mapearResposta(T model);


    public ResponseEntity<Resposta> encontrarById(UUID id) {
        return repository.findById(id)
                .map(data -> {
                    var resposta = mapearResposta(data);
                    return new Resposta<R>("Dados carregados com sucesso!", resposta).sucesso();
                }
        ).orElse( new Resposta<>("Não encontramos os dados!", null).naoEncontrado());
    }
    public ResponseEntity<Resposta> listar() {
        var list = repository.findAll();
        return list.isEmpty()?
                new Resposta<R>("Não encontramos os dados!", null).naoEncontrado()
                : new Resposta<List<R>>("Os dados foram carregados com sucesso!",
                        list.stream()
                                .filter(
                                        data-> !((GenericId) data).getStatus().equals(Status.DELETED)
                                )
                                .map(this::mapearResposta)
                                .collect(Collectors.toList())).sucesso();
    }
    public ResponseEntity<Resposta> listarDeletados() {
        var list = repository.findAll();
        var dataFiltered =   list.stream()
                .filter(
                        data-> ((GenericId) data).getStatus().equals(Status.DELETED)
                )
                .map(this::mapearResposta)
                .collect(Collectors.toList());
        return dataFiltered.isEmpty()?
                new Resposta<R>("Não encontramos os dados!", null).naoEncontrado()
                : new Resposta<List<R>>("Os dados foram carregados com sucesso!",
                dataFiltered).sucesso();
    }

    public ResponseEntity<Resposta> paginar(Pageable pageable) {

        var list = repository.findAll();
        var dataFiltered = list.stream()
                .filter(
                        data-> !((GenericId) data).getStatus().equals(Status.DELETED)
                )
                .map(this::mapearResposta)
                .collect(Collectors.toList());

        return dataFiltered.isEmpty()?
                new Resposta<R>("Não encontramos os dados!", null).naoEncontrado()
                :new Resposta<Page<R>>("Os dados foram carregados com sucesso!",
                new PageImpl(dataFiltered, pageable, pageable.getPageNumber())
                ).sucesso();
    }


    public abstract ResponseEntity<Resposta> criar(EntidadeRequisicaoAbstract<T> entityRequest);

    public abstract ResponseEntity<Resposta> actualizar(UUID id, EntidadeRequisicaoAbstract<T> entityRequest);

    public abstract ResponseEntity<Resposta> deletar(UUID id);


    protected ResponseEntity<Resposta> criarPadrao(EntidadeRequisicaoAbstract<T> entityRequest) {
        if (!entityRequest.isValido()) {
            return new Resposta<R>(entityRequest.getMensagemErro(), null).recusado();
        }
        return new Resposta<R>("Nova informação adicionada!", mapearResposta(repository.save(entityRequest.mapearEntidade()))).sucesso();
    }

    public long total() {
        return repository.count();
    }
    protected ResponseEntity<Resposta> actualizarPadrao(UUID id, EntidadeRequisicaoAbstract<T> entityRequest) {


        if (!entityRequest.isValido()) {
            return new Resposta<R>(entityRequest.getMensagemErro(), null).recusado();
        }

        T entityFound = repository.findById(id).orElse(null);
        if (entityFound == null) {
            return new Resposta<R>("Falha ao encontrar os dados!", null).naoEncontrado();
        }


        var entity =  entityRequest.mapearEntidade();
        ((GenericId) entity).setId(id);

        return new Resposta<>("Actualização efectuada!", mapearResposta(repository.save(entity))).sucesso();
    }
    protected ResponseEntity<Resposta> deletarPadrao(UUID id) {

        T entityFound = repository.findById(id).orElse(null);
        if (entityFound == null) {
            return new Resposta<R>("Falha ao encontrar os dados!", null).naoEncontrado();
        }

        ((GenericId)entityFound).setStatus(Status.DELETED);
        return new Resposta<R>("Informação apagada!", mapearResposta(repository.save(entityFound))).sucesso();
    }
}
