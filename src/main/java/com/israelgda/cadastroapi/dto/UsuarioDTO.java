package com.israelgda.cadastroapi.dto;

import com.israelgda.cadastroapi.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class UsuarioDTO {

    private Long id;
    private String nome;
    private String dataNascimento;
    private String cidade;
    private String bairro;
    private String estado;
    private String cpf;
    private String telefone;

    public UsuarioDTO(Usuario usuario){
        id = usuario.getId();
        nome = usuario.getNome();
        dataNascimento = usuario.getDataNascimento();
        cidade = usuario.getCidade();
        bairro = usuario.getBairro();
        estado = usuario.getEstado();
        cpf = usuario.getCpf();
        telefone = usuario.getTelefone();
    }
}
