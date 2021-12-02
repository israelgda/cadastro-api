package com.israelgda.cadastroapi.constants;

import com.israelgda.cadastroapi.dto.DadosDTO;
import com.israelgda.cadastroapi.dto.UsuarioDTO;
import com.israelgda.cadastroapi.entities.Usuario;

public class Constants {

    public static Usuario createUsuario(){
        Usuario usuario = Usuario.builder()
                                .id(1L)
                                .nome("Israel")
                                .dataNascimento("08/05/1997")
                                .cidade("Maceió")
                                .bairro("Serraria")
                                .estado("AL")
                                .cpf("11211311499")
                                .telefone("82981110888")
                                .build();
        return usuario;
    }

    public static Usuario createUsuarioAtualizado() {
        Usuario usuarioAtualizado = Usuario.builder()
                .id(1L)
                .nome("Marcos")
                .dataNascimento("08/05/1997")
                .cidade("Maceió")
                .bairro("Antares")
                .estado("AL")
                .cpf("11211311499")
                .telefone("82999999999")
                .build();

        return usuarioAtualizado;
    }

    public static UsuarioDTO createUsuarioAtualizadoDTO(){
        Usuario usuario = createUsuarioAtualizado();
        return new UsuarioDTO(usuario);
    }

    public static UsuarioDTO createUsuarioDTO(){
        Usuario usuario = createUsuario();
        return new UsuarioDTO(usuario);
    }

    public static DadosDTO createDadosDTO() {
        DadosDTO dadosDTO = DadosDTO.builder()
                                    .nome("Israel")
                                    .dataNascimento("08/05/1997")
                                    .cep("57046340")
                                    .cpf("11211311499")
                                    .telefone("82981110888")
                                    .build();
        return dadosDTO;
    }

    public static DadosDTO createDadosDTOCepInexistente() {
        DadosDTO dadosDTO = DadosDTO.builder()
                .nome("Israel")
                .dataNascimento("08/05/1997")
                .cep("99999999")
                .cpf("11211311499")
                .telefone("82981110888")
                .build();
        return dadosDTO;
    }

    public static DadosDTO createDadosDTOCepInvalido() {
        DadosDTO dadosDTO = DadosDTO.builder()
                .nome("Israel")
                .dataNascimento("08/05/1997")
                .cep("999999998")
                .cpf("11211311499")
                .telefone("82981110888")
                .build();
        return dadosDTO;
    }

    public static DadosDTO createDadosDTODataInvalida() {
        DadosDTO dadosDTO = DadosDTO.builder()
                .nome("Israel")
                .dataNascimento("08/13/1997")
                .cep("57046340")
                .cpf("11211311499")
                .telefone("82981110888")
                .build();
        return dadosDTO;
    }

    public static DadosDTO createDadosDTONomeInvalido() {
        DadosDTO dadosDTO = DadosDTO.builder()
                .nome("Is")
                .dataNascimento("08/05/1997")
                .cep("57046340")
                .cpf("11211311499")
                .telefone("82981110888")
                .build();
        return dadosDTO;
    }

    public static DadosDTO createDadosDTOCpfJaCadastrado() {
        DadosDTO dadosDTO = DadosDTO.builder()
                .nome("Israel")
                .dataNascimento("08/05/1997")
                .cep("57046340")
                .cpf("11211311499")
                .telefone("82981110888")
                .build();
        return dadosDTO;
    }
}
