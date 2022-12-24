package br.com.banco.services.interfaces;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import br.com.banco.dto.TransferenciaDto;

public interface TransferenciaService {
    List<TransferenciaDto> obterTransferencias();
    TransferenciaDto cadastrarTransferencia(TransferenciaDto TransferenciaDto);
    Optional<TransferenciaDto> obterTransferenciaPorId(Long id);
    void excluirTransferencia(Long id);
    TransferenciaDto atualizarTransferencia(Long id, TransferenciaDto novaTransferenciaDto);
    List<TransferenciaDto> obterTodasTransferenciasPorContaBancaria(Long id);
    List<TransferenciaDto> obterTodasTransferenciasPorPeriodo(LocalDateTime data_inicio, LocalDateTime data_fim, String nome_operador_transacao);
    List<TransferenciaDto> obterTransferenciasPorNomeOperadorTransacao(String nome_operador_transacao);
    BigDecimal calcularSaldoTransferenciasPorPeriodo(LocalDateTime data_inicio, LocalDateTime data_fim, String nome_operador_transacao);
    BigDecimal calcularSaldoTransferenciasPorNomeOperadorTransacao(String nome_operador_transacao);
}
