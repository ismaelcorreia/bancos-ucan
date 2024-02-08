package edu.ucan.sdp2.bancoengenhariaservice.controllers;

import edu.ucan.sdp2.bancoengenhariaservice.dto.Resposta;
import edu.ucan.sdp2.bancoengenhariaservice.services.ServicoGenerico;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public abstract class ControladorGenerico<T, R>{

    protected ServicoGenerico<T, R> serviceApi;

    @GetMapping
    public ResponseEntity<Resposta> listAll(){
        return  serviceApi.listar();
    }

    @GetMapping(params = "id")
    public ResponseEntity<Resposta> find(
            @RequestParam String id) {
        return this.serviceApi.encontrarById(UUID.fromString(id));
    }


    @GetMapping("/eliminados")
    public ResponseEntity<Resposta> eliminados(){
        return  serviceApi.listarDeletados();
    }

    @GetMapping( "/paginas")
    public ResponseEntity<Resposta> paginas(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "15") int size
    ){
        return  serviceApi.paginar(PageRequest.of(page, size));
    }

    @DeleteMapping(params = "id")
    public ResponseEntity<Resposta> eliminar(
            @RequestParam String id) {
        return this.serviceApi.deletar(UUID.fromString(id));
    }
}


