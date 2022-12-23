package br.com.banco.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class TransferenciaDto {
    
    @NotBlank
    private String nome_operador_transacao;
    @NotBlank
    private String tipo;
    @NotBlank
    private BigDecimal valor;

}
