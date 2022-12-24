package br.com.banco.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class TransferenciaDto {
    
    @NotBlank
    private String nome_operador_transacao;
    @NotBlank
    private String tipo;
    @NotBlank
    private BigDecimal valor;    
    private BigDecimal saldo_total;
    private BigDecimal saldo_total_periodo;
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDateTime data_transferencia;

}
