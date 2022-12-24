package br.com.banco.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.banco.models.TransferenciaModel;

@Repository
public interface TransferenciaRepository extends JpaRepository <TransferenciaModel, Long>{
           
    @Query("FROM transferencia t INNER JOIN conta c ON t.conta_id=c.id_conta WHERE c.id_conta = :id_conta")
    List<TransferenciaModel> buscarTransferenciasPorContaBancaria(Long id_conta);

    @Query("FROM transferencia t INNER JOIN conta c ON t.conta_id=c.id_conta WHERE nome_operador_transacao = :nome_operador_transacao")
    List<TransferenciaModel> buscarTransferenciasPorNomeOperadorTransacao(String nome_operador_transacao);
    
    @Query("FROM transferencia t INNER JOIN conta c ON t.conta_id=c.id_conta WHERE t.data_transferencia >= :data_inicio AND t.data_transferencia <= :data_fim AND nome_operador_transacao = :nome_operador_transacao")
    List<TransferenciaModel> buscarTransferenciasPorPeriodo(LocalDateTime data_inicio, LocalDateTime data_fim, String nome_operador_transacao);
    
    @Query(value = "SELECT sum(t.valor) FROM transferencia t WHERE t.data_transferencia >= :data_inicio AND t.data_transferencia <= :data_fim AND nome_operador_transacao = :nome_operador_transacao", nativeQuery = true)    
    BigDecimal calcularSaldoTransferenciasPorPeriodo(LocalDateTime data_inicio, LocalDateTime data_fim, String nome_operador_transacao);

    // @Query(value = "SELECT SUM(t.valor) FROM transferencia t", nativeQuery = true)    
    @Query(value = "SELECT SUM(t.valor) FROM transferencia t WHERE nome_operador_transacao = :nome_operador_transacao", nativeQuery = true)    
    BigDecimal calcularSaldoTransferenciasPorNomeOperadorTransacao(String nome_operador_transacao);
}