package com.israelgda.cadastroapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class DadosDTO {

    private String nome;
    private Date dataNascimento;
    private String cep;
    private String cpf;
    private String telefone;

    public DadosDTO(){

    }
}
