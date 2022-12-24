package br.com.banco.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import br.com.banco.dto.ContaDto;
import br.com.banco.models.ContaModel;
import br.com.banco.repository.ContaRepository;
import br.com.banco.services.interfaces.ContaService;
import lombok.Data;

@Data
@Service
public class ContaServiceImpl implements ContaService {

    final ContaRepository contaRepository;

    @Transactional
    @Override
    public ContaDto cadastrarConta(ContaDto ContaDto) {
        ContaModel ContaModel = new ModelMapper().map(ContaDto, ContaModel.class);        
        ContaModel = contaRepository.save(ContaModel);
        return new ModelMapper().map(ContaModel, ContaDto.class);
    }

    @Transactional
    @Override
    public void excluirConta(Long id) {
        contaRepository.deleteById(id);
    }
    
    @Override
    public Optional<ContaDto> obterContaPorId(Long id) {
        Optional<ContaModel> ContaModel = contaRepository.findById(id);
        return Optional.of(new ModelMapper().map(ContaModel.get(), ContaDto.class));
    }

    @Override
    public List<ContaDto> obterContas() {
        List<ContaModel> listaContasModel = contaRepository.findAll(); 
       
        return  listaContasModel.stream()
        .map( t -> new ModelMapper().map(t, ContaDto.class))
        .collect(Collectors.toList());
    }

    @Override
    public ContaDto atualizarConta(Long id, ContaDto ContaDto) {
        ContaModel ContaModel = new ModelMapper().map(ContaDto, ContaModel.class);
        ContaModel.setId_conta(id);
        ContaModel = contaRepository.save(ContaModel);
        return new ModelMapper().map(ContaModel, ContaDto.class);
    }
    
}
