package br.com.banco.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.banco.models.TransferenciaModel;

@Repository
public interface TransferenciaRepository extends JpaRepository <TransferenciaModel, Long>{
       
    // SELECT * FROM TRANSFERENCIA  T INNER JOIN CONTA C ON T.CONTA_ID=C.ID_CONTA WHERE C.ID_CONTA  = 1
    @Query("FROM transferencia t INNER JOIN conta c ON t.conta_id=c.id_conta WHERE c.id_conta = :id_conta")
    List<TransferenciaModel> buscarTransferenciasPorContaBancaria(Long id_conta);

    // SELECT * FROM TRANSFERENCIA T INNER JOIN CONTA C ON T.CONTA_ID=C.ID_CONTA  WHERE DATA_TRANSFERENCIA >= '2018-04-01T06:12:04Z'  AND DATA_TRANSFERENCIA <= '2022-04-01T06:12:04Z' AND NOME_OPERADOR_TRANSACAO = 'Beltrano'' 
    @Query("FROM transferencia t INNER JOIN conta c ON t.conta_id=c.id_conta WHERE t.data_transferencia >= :data_inicio AND t.data_transferencia <= :data_fim AND nome_operador_transacao = :nome_operador_transacao")
    List<TransferenciaModel> buscarTransferenciasPorPeriodo(LocalDateTime data_inicio, LocalDateTime data_fim, String nome_operador_transacao);
}