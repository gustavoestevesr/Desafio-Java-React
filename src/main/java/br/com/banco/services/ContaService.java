package br.com.banco.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.banco.models.ContaModel;
import br.com.banco.repository.ContaRepository;
import lombok.Data;

@Data
@Service
public class ContaService {

    final ContaRepository contaRepository;

    @Transactional
    public ContaModel save(ContaModel contaModel) {
        return contaRepository.save(contaModel);
    }

    @Transactional
    public void delete(ContaModel contaModel) {        
        contaRepository.delete(contaModel);
    }
    
    public Optional<ContaModel> findById(Long id_conta) {
        return contaRepository.findById(id_conta);
    }

    public Page<ContaModel> findAll(Pageable pageable) {
        return contaRepository.findAll(pageable);
    }
    
}
