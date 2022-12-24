package br.com.banco.services.interfaces;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import br.com.banco.dto.TransferenciaDto;

public interface ContaService {
    List<TransferenciaDto> obterTransferencias();
    TransferenciaDto cadastrarTransferencia(TransferenciaDto TransferenciaDto);
    Optional<TransferenciaDto> obterTransferenciaPorId(Long id);
    void excluirTransferencia(Long id);
    TransferenciaDto atualizarTransferencia(Long id, TransferenciaDto novaTransferenciaDto);
}
