package br.com.banco.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

import br.com.banco.dto.TransferenciaDto;
import br.com.banco.models.TransferenciaModel;
import br.com.banco.services.TransferenciaService;
import lombok.Data;

@Data
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/transferencias")
public class TransferenciaController {

    final TransferenciaService transferenciaService;

    @PostMapping
    public ResponseEntity<Object> salvarTransferencia(@RequestBody @Valid TransferenciaDto transactionDto){        
        var transactionModel = new TransferenciaModel();
        BeanUtils.copyProperties(transactionDto, transactionModel);  
        transactionModel.setData_transferencia(LocalDateTime.now(ZoneId.of("BET")));      
        return ResponseEntity.status(HttpStatus.CREATED).body(transferenciaService.save(transactionModel));
    }

    @GetMapping
    public ResponseEntity<Page<TransferenciaModel>> obterTodasTransferencias(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(transferenciaService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> obterTransferenciaPorID(@PathVariable(value = "id") Long id){
        Optional<TransferenciaModel> transferenciaModelOptional = transferenciaService.findById(id);
        if (!transferenciaModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(transferenciaModelOptional.get());
    } 

    

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removerTransferenciaPorID(@PathVariable(value = "id") Long id){
        Optional<TransferenciaModel> transferenciaModelOptional = transferenciaService.findById(id);
        if (!transferenciaModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found.");
        }
        transferenciaService.delete(transferenciaModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Transaction deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarTransferenciaPorID(@PathVariable(value = "id") Long id,
                                                    @RequestBody @Valid TransferenciaDto transactionDto){
        Optional<TransferenciaModel> transactionModelOptional = transferenciaService.findById(id);
        if (!transactionModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found.");
        }
        var transferenciaModel = new TransferenciaModel();
        BeanUtils.copyProperties(transactionDto, transferenciaModel);
        transferenciaModel.setId(transactionModelOptional.get().getId());
        transferenciaModel.setData_transferencia(transactionModelOptional.get().getData_transferencia());
        return ResponseEntity.status(HttpStatus.OK).body(transferenciaService.save(transferenciaModel));
    }

}