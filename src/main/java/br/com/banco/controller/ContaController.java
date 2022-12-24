package br.com.banco.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

import br.com.banco.dto.ContaDto;
import br.com.banco.models.ContaModel;
import br.com.banco.services.ContaServiceImpl;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/contas")
public class ContaController {

    final ContaServiceImpl contaService;

    public ContaController(ContaServiceImpl contaService) {
        this.contaService = contaService;
    }

    @PostMapping
    public ResponseEntity<ContaDto> salvarConta(@RequestBody @Valid ContaDto contaDto){        
        return new ResponseEntity<>(contaService.cadastrarConta(contaDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ContaDto>> obterTodasContas(){
        return new ResponseEntity<>(contaService.obterContas(), HttpStatus.FOUND);        
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDto> obterContaPorID(@PathVariable(value = "id") Long id){
        Optional<ContaDto> ContaDtoOptional = contaService.obterContaPorId(id);
        if (!ContaDtoOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ContaDtoOptional.get(), HttpStatus.FOUND);        
    } 

    @PutMapping("/{id}")
    public ResponseEntity<ContaDto> atualizarContaPorID(@PathVariable(value = "id") Long id,
                                                    @RequestBody @Valid ContaDto ContaDto){
        Optional<ContaDto> ContaDtoOptional = contaService.obterContaPorId(id);
        if (!ContaDtoOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(contaService.atualizarConta(id, ContaDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerContaPorID(@PathVariable(value = "id") Long id){
        contaService.excluirConta(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}