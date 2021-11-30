package com.israelgda.cadastroapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class EnderecoDTO {

    private String bairro;
    private String localidade;
    private String uf;
    private String erro;

}
