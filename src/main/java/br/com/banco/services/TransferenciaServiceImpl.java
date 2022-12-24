package br.com.banco.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.banco.dto.TransferenciaDto;
import br.com.banco.models.TransferenciaModel;
import br.com.banco.repository.TransferenciaRepository;
import br.com.banco.services.interfaces.TransferenciaService;
import lombok.Data;

@Data
@Service
public class TransferenciaServiceImpl implements TransferenciaService {

    final TransferenciaRepository transferenciaRepository;

    @Transactional
    @Override
    public TransferenciaDto cadastrarTransferencia(TransferenciaDto transferenciaDto) {
        TransferenciaModel transferenciaModel = new ModelMapper().map(transferenciaDto, TransferenciaModel.class);
        transferenciaModel.setData_transferencia(LocalDateTime.now(ZoneId.of("BET")));
        transferenciaModel = transferenciaRepository.save(transferenciaModel);
        return new ModelMapper().map(transferenciaModel, TransferenciaDto.class);
    }

    @Transactional
    @Override
    public void excluirTransferencia(Long id) {
        transferenciaRepository.deleteById(id);
    }
    
    @Override
    public Optional<TransferenciaDto> obterTransferenciaPorId(Long id) {
        Optional<TransferenciaModel> transferenciaModel = transferenciaRepository.findById(id);
        return Optional.of(new ModelMapper().map(transferenciaModel.get(), TransferenciaDto.class));
    }

    @Override
    public List<TransferenciaDto> obterTransferencias() {
        List<TransferenciaModel> listaTransferenciasModel = transferenciaRepository.findAll(); 
       
        return  listaTransferenciasModel.stream()
        .map( t -> new ModelMapper().map(t, TransferenciaDto.class))
        .collect(Collectors.toList());
    }

    @Override
    public TransferenciaDto atualizarTransferencia(Long id, TransferenciaDto transferenciaDto) {
        TransferenciaModel transferenciaModel = new ModelMapper().map(transferenciaDto, TransferenciaModel.class);
        transferenciaModel.setId(id);
        transferenciaModel = transferenciaRepository.save(transferenciaModel);
        return new ModelMapper().map(transferenciaModel, TransferenciaDto.class);
    }

    @Override
    public List<TransferenciaDto> obterTodasTransferenciasPorContaBancaria(Long id) {

        List<TransferenciaModel> transferenciasPorContaBancariaModel = transferenciaRepository.buscarTransferenciasPorContaBancaria(id);               

        return transferenciasPorContaBancariaModel.stream()
        .map( t -> new ModelMapper().map(t, TransferenciaDto.class))
        .collect(Collectors.toList());

    }

    @Override
    public List<TransferenciaDto> obterTransferenciasPorNomeOperadorTransacao(String nome_operador_transacao) {
        List<TransferenciaModel> transferenciasPorNomeOperadorTransacaoModel = transferenciaRepository.buscarTransferenciasPorNomeOperadorTransacao(nome_operador_transacao);

        return transferenciasPorNomeOperadorTransacaoModel.stream()
        .map( t -> new ModelMapper().map(t, TransferenciaDto.class))
        .collect(Collectors.toList());
    }

    @Override
    public List<TransferenciaDto> obterTodasTransferenciasPorPeriodo(LocalDateTime data_inicio, LocalDateTime data_fim, String nome_operador_transacao) {
        List<TransferenciaModel> transferenciasTransferenciasPorPeriodoModel = transferenciaRepository.buscarTransferenciasPorPeriodo(data_inicio, data_fim, nome_operador_transacao);       

        List<TransferenciaDto> transferenciasTransferenciasPorPeriodoDto =  transferenciasTransferenciasPorPeriodoModel.stream()
        .map( t -> new ModelMapper().map(t, TransferenciaDto.class))
        .collect(Collectors.toList());

        transferenciasTransferenciasPorPeriodoDto.stream()
        .forEach( t -> t.setSaldo_total_periodo( calcularSaldoTransferenciasPorPeriodo(data_inicio, data_fim, nome_operador_transacao) ));

        transferenciasTransferenciasPorPeriodoDto.stream()
        .forEach( t -> t.setSaldo_total( calcularSaldoTransferenciasPorNomeOperadorTransacao(nome_operador_transacao) ));

        return transferenciasTransferenciasPorPeriodoDto;
    }

    @Override
    public BigDecimal calcularSaldoTransferenciasPorPeriodo(LocalDateTime data_inicio, LocalDateTime data_fim, String nome_operador_transacao){
        return transferenciaRepository.calcularSaldoTransferenciasPorPeriodo(data_inicio, data_fim, nome_operador_transacao);
    }

    @Override
    public BigDecimal calcularSaldoTransferenciasPorNomeOperadorTransacao(String nome_operador_transacao){
        return transferenciaRepository.calcularSaldoTransferenciasPorNomeOperadorTransacao(nome_operador_transacao);
    }
    
}
