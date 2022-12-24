package br.com.banco.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.banco.dto.TransferenciaDto;
import br.com.banco.models.ContaModel;
import br.com.banco.services.ContaService;
import br.com.banco.services.TransferenciaServiceImpl;
import lombok.Data;

@Data
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/transferencias")
public class TransferenciaController {

    final TransferenciaServiceImpl transferenciaService;

    @PostMapping
    public ResponseEntity<TransferenciaDto> cadastrarTransferencia(@RequestBody @Valid TransferenciaDto transferenciaDto){                    
        return new ResponseEntity<>(transferenciaService.cadastrarTransferencia(transferenciaDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TransferenciaDto>> obterTodasTransferencias(){
        return new ResponseEntity<>(transferenciaService.obterTransferencias(), HttpStatus.FOUND);        
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferenciaDto> obterTransferenciaPorID(@PathVariable(value = "id") Long id){
        Optional<TransferenciaDto> transferenciaDtoOptional = transferenciaService.obterTransferenciaPorId(id);
        if (!transferenciaDtoOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(transferenciaDtoOptional.get(), HttpStatus.FOUND);        
    } 

    @PutMapping("/{id}")
    public ResponseEntity<TransferenciaDto> atualizarTransferenciaPorID(@PathVariable(value = "id") Long id,
                                                    @RequestBody @Valid TransferenciaDto transferenciaDto){
        Optional<TransferenciaDto> transferenciaDtoOptional = transferenciaService.obterTransferenciaPorId(id);
        if (!transferenciaDtoOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(transferenciaService.atualizarTransferencia(id, transferenciaDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerTransferenciaPorID(@PathVariable(value = "id") Long id){
        transferenciaService.excluirTransferencia(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/numerocontabancaria/{id_conta}")
    public ResponseEntity<List<TransferenciaDto>> obterTodasTransferenciasPorContaBancaria(@PathVariable(value = "id_conta") Long id_conta){
        return ResponseEntity.status(HttpStatus.FOUND).body(transferenciaService.obterTodasTransferenciasPorContaBancaria(id_conta));
    }  

    @GetMapping("/nomeoperadortransacao/{nome_operador_transacao}")
    public ResponseEntity<List<TransferenciaDto>> obterTransferenciasPorNomeOperadorTransacao(@PathVariable(value = "nome_operador_transacao") String nome_operador_transacao){
        return ResponseEntity.status(HttpStatus.FOUND).body(transferenciaService.obterTransferenciasPorNomeOperadorTransacao(nome_operador_transacao));
    }  

    @GetMapping("/periodo/{data_inicio}/{data_fim}/{nome_operador_transacao}")
    public ResponseEntity<List<TransferenciaDto>> obterTodasTransferenciasPorPeriodo(@PathVariable(value = "data_inicio") String data_inicio, @PathVariable(value = "data_fim") String data_fim, @PathVariable(value = "nome_operador_transacao") String nome_operador_transacao){

        LocalDateTime conv_data_inicio = LocalDateTime.parse(data_inicio,
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));

        LocalDateTime conv_data_fim = LocalDateTime.parse(data_fim,
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));   
        
        return ResponseEntity.status(HttpStatus.OK).body(transferenciaService.obterTodasTransferenciasPorPeriodo(conv_data_inicio, conv_data_fim, nome_operador_transacao));
    }     

}