package br.com.banco.controller;

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
import br.com.banco.services.ContaService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/contas")
public class ContaController {

    final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping
    public ResponseEntity<Object> salvarConta(@RequestBody @Valid ContaDto ContaDto){        
        var contaModel = new ContaModel();
        BeanUtils.copyProperties(ContaDto, contaModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(contaService.save(contaModel));
    }

    @GetMapping
    public ResponseEntity<Page<ContaModel>> obterTodasContas(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(contaService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> obterContaPorID(@PathVariable(value = "id") Long id){
        Optional<ContaModel> contaModelOptional = contaService.findById(id);
        if (!contaModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(contaModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removerContaPorID(@PathVariable(value = "id") Long id){
        Optional<ContaModel> contaModelOptional = contaService.findById(id);
        if (!contaModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found.");
        }
        contaService.delete(contaModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Account deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarContaPorID(@PathVariable(value = "id") Long id,
                                                    @RequestBody @Valid ContaDto contaDto){
        Optional<ContaModel> contaModelOptional = contaService.findById(id);
        if (!contaModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found.");
        }
        var contaModel = new ContaModel();
        BeanUtils.copyProperties(contaDto, contaModel);
        contaModel.setId_conta(contaModelOptional.get().getId_conta());
        return ResponseEntity.status(HttpStatus.OK).body(contaService.save(contaModel));
    }

}