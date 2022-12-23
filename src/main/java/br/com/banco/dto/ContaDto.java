package br.com.banco.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ContaDto {
    
    @NotBlank
    private String nome_responsavel;

}
