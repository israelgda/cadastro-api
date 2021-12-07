package com.israelgda.cadastroapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@Builder
public class DadosDTO {

    @NotBlank
    @Size(min = 3, max = 100, message = "Nome deve conter entre 3 a 100 caracteres.")
    private String nome;

    @Pattern(regexp = "(((0[1-9]|[12][0-9]|3[01])([/])(0[13578]|10|12)([/])(\\d{4}))|(([0][1-9]|[12][0-9]|30)([/])(0[469]|11)([/])(\\d{4}))|((0[1-9]|1[0-9]|2[0-8])([/])(02)([/])(\\d{4}))|((29)(\\.|-|\\/)(02)([/])([02468][048]00))|((29)([/])(02)([/])([13579][26]00))|((29)([/])(02)([/])([0-9][0-9][0][48]))|((29)([/])(02)([/])([0-9][0-9][2468][048]))|((29)([/])(02)([/])([0-9][0-9][13579][26])))", message = "Data Inválida! Verifique a data informada. Formato dd/mm/aaaa.")
    private String dataNascimento;
    private String cep;

    @Size(min = 11, max = 11, message = "O cpf deve conter 11 digitos, apenas números.")
    @Pattern(regexp = "(\\d{3}.?\\d{3}.?\\d{3}-?\\d{2})", message = "O cpf deve conter 11 digitos, apenas números.")
    private String cpf;

    //@Size(min = 11, max = 11, message = "Telefone deve conter 11 digitos, apenas números.")
    //@Pattern(regexp = "^[\"]{1}[0-9]{11}[\"]{1}$", message = "Telefone deve conter 11 digitos, apenas números.")
    @Pattern(regexp = "^[0-9]{11}$", message = "Telefone deve conter 11 digitos, apenas números.")
    private String telefone;

    public DadosDTO(){

    }
}
