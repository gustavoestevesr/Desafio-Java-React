package br.com.banco.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.banco.models.TransferenciaModel;
import br.com.banco.repository.TransferenciaRepository;

@Service
public class TransferenciaService {

    final TransferenciaRepository transferenciaRepository;

    public TransferenciaService(TransferenciaRepository transferenciaRepository){
        this.transferenciaRepository = transferenciaRepository;
    }

    @Transactional
    public TransferenciaModel save(TransferenciaModel transferenciaModel) {
        return transferenciaRepository.save(transferenciaModel);
    }

    @Transactional
    public void delete(TransferenciaModel transferenciaModel) {        
        transferenciaRepository.delete(transferenciaModel);
    }
    
    public Optional<TransferenciaModel> findById(Long id) {
        return transferenciaRepository.findById(id);
    }

    public Page<TransferenciaModel> findAll(Pageable pageable) {
        return transferenciaRepository.findAll(pageable);
    }

    
}
