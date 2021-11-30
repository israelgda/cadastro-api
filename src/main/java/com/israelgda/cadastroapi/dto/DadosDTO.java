package com.israelgda.cadastroapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DadosDTO {

    private String nome;
    private String dataNascimento;
    private String cep;
    private String cpf;
    private String telefone;

    public DadosDTO(){
        
    }
}
