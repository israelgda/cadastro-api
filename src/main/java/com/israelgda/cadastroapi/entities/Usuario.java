package com.israelgda.cadastroapi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Usuario {

    private Long id;
    private String nome;
    private String dataNascimento;
    private String cidade;
    private String bairro;
    private String estado;
    private String cpf;
    private String email;

    public Usuario(){

    }
}
