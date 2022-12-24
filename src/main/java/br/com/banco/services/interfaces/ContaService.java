package br.com.banco.services.interfaces;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import br.com.banco.dto.ContaDto;

public interface ContaService {
    List<ContaDto> obterContas();
    ContaDto cadastrarConta(ContaDto ContaDto);
    Optional<ContaDto> obterContaPorId(Long id);
    void excluirConta(Long id);
    ContaDto atualizarConta(Long id, ContaDto novaContaDto);
}
