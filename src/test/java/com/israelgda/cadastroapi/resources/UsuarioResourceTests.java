package com.israelgda.cadastroapi.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.israelgda.cadastroapi.constants.Constants;
import com.israelgda.cadastroapi.dto.DadosDTO;
import com.israelgda.cadastroapi.dto.UsuarioDTO;
import com.israelgda.cadastroapi.services.UsuarioService;
import com.israelgda.cadastroapi.services.exceptions.*;
import lombok.experimental.StandardException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UsuarioResource.class)
public class UsuarioResourceTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioService usuarioService;

    private String existingDocument;
    private String nonExistingDocument;
    private Long existingId;
    private Long notExistingId;
    private UsuarioDTO usuarioDto;
    private UsuarioDTO usuarioAtualizadoDto;
    private DadosDTO dadosDTO;
    private DadosDTO dadosDTOCepInexistente;
    private DadosDTO dadosDTOCepInvalido;

    @BeforeEach
    void setUp() throws PostalCodeNotFound {
        existingDocument = "18bc6237d";
        nonExistingDocument = "aaa000";
        existingId = 1L;
        notExistingId = 999L;
        usuarioDto = Constants.createUsuarioDTO();
        usuarioAtualizadoDto = Constants.createUsuarioAtualizadoDTO();
        dadosDTO = Constants.createDadosDTO();
        dadosDTOCepInexistente = Constants.createDadosDTOCepInexistente();
        dadosDTOCepInvalido = Constants.createDadosDTOCepInvalido();
    }

    @Test
    public void findByCpfoDeveRetornarUsuarioDtoQuandoIdExistir() throws Exception {
        when(usuarioService
                .findByCpf(existingDocument))
                .thenReturn(usuarioDto);

        ResultActions result =
                mockMvc.perform(get("/v1/usuarios/{cpf}", existingDocument)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.nome").value("Israel"));
        result.andExpect(jsonPath("$.dataNascimento").value("08/05/1997"));
        result.andExpect(jsonPath("$.cpf").value("11211311499"));
        result.andExpect(jsonPath("$.telefone").value("82981110888"));
        result.andExpect(jsonPath("$.cidade").value("Maceió"));
        result.andExpect(jsonPath("$.bairro").value("Serraria"));
        result.andExpect(jsonPath("$.estado").value("AL"));

    }

    @Test
    public void findByCpfoDeveRetornarNotFoundQuandoIdNaoExistir() throws Exception {
        when(usuarioService
                .findByCpf(nonExistingDocument))
                .thenThrow(ResourceNotFoundException.class);

        ResultActions result =
                mockMvc.perform(get("/v1/usuarios/{number}", nonExistingDocument)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void createDeveRetornarStatusCreatedEUsuarioDto() throws Exception {
        when(usuarioService
                .create(any()))
                .thenReturn(usuarioDto);

        String jsonBody = objectMapper.writeValueAsString(dadosDTO);

        ResultActions result =
                mockMvc.perform(post("/v1/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.nome").value("Israel"));
        result.andExpect(jsonPath("$.dataNascimento").value("08/05/1997"));
        result.andExpect(jsonPath("$.cpf").value("11211311499"));
        result.andExpect(jsonPath("$.telefone").value("82981110888"));
        result.andExpect(jsonPath("$.cidade").value("Maceió"));
        result.andExpect(jsonPath("$.bairro").value("Serraria"));
        result.andExpect(jsonPath("$.estado").value("AL"));

    }

    @Test
    public void createDeveRetornarNotFoundQuandoCepNaoExistir() throws Exception {
        when(usuarioService
                .create(any()))
                .thenThrow(PostalCodeNotFound.class);

        String jsonBody = objectMapper.writeValueAsString(dadosDTO);

        ResultActions result =
                mockMvc.perform(post("/v1/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void createDeveRetornarBadRequestQuandoCepForInvalido() throws Exception {
        when(usuarioService
                .create(any()))
                .thenThrow(PostalCodeInvalidFormatException.class);

        String jsonBody = objectMapper.writeValueAsString(dadosDTO);

        ResultActions result =
                mockMvc.perform(post("/v1/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());
    }

    @Test
    public void createDeveRetornarBadRequestQuandoCpfJaCadastrado() throws Exception {
        when(usuarioService
                .create(any()))
                .thenThrow(CpfAlredyRegistered.class);

        String jsonBody = objectMapper.writeValueAsString(dadosDTO);

        ResultActions result =
                mockMvc.perform(post("/v1/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());
    }

    @Test
    public void createDeveRetornarBadRequestQuandoDataNascimentoInvalida() throws Exception {
        when(usuarioService
                .create(any()))
                .thenThrow(BirthDateInvalidFormatException.class);

        String jsonBody = objectMapper.writeValueAsString(dadosDTO);

        ResultActions result =
                mockMvc.perform(post("/v1/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());
    }

    @Test
    public void createDeveRetornarBadRequestQuandoDadosInvalidos() throws Exception {
        when(usuarioService
                .create(any()))
                .thenThrow(DataFormatViolationException.class);

        String jsonBody = objectMapper.writeValueAsString(dadosDTO);

        ResultActions result =
                mockMvc.perform(post("/v1/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());
    }


    @Test
    public void updateDeveRetornarUsuarioDtoQuandoIdExistir() throws Exception {
        when(usuarioService
                .update(1L, usuarioAtualizadoDto))
                .thenReturn(usuarioAtualizadoDto);

        String jsonBody = objectMapper.writeValueAsString(usuarioAtualizadoDto);

        ResultActions result =
                mockMvc.perform(put("/v1/usuarios/{id}", 1l)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.nome").value("Marcos"));
        result.andExpect(jsonPath("$.dataNascimento").value("08/05/1997"));
        result.andExpect(jsonPath("$.cpf").value("11211311499"));
        result.andExpect(jsonPath("$.telefone").value("82999999999"));
        result.andExpect(jsonPath("$.cidade").value("Maceió"));
        result.andExpect(jsonPath("$.bairro").value("Antares"));
        result.andExpect(jsonPath("$.estado").value("AL"));

    }

    @Test
    public void updateDeveRetornarNotFoundQuandoIdNaoExistir() throws Exception {
        when(usuarioService
                .update(999L, usuarioAtualizadoDto))
                .thenThrow(ResourceNotFoundException.class);

        String jsonBody = objectMapper.writeValueAsString(usuarioAtualizadoDto);

        ResultActions result =
                mockMvc.perform(put("/v1/usuarios/{id}", 999L)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteRetornarNoContentQuandoIdExistir() throws Exception {
        doNothing()
                .when(usuarioService)
                .delete(existingId);

        ResultActions result =
                mockMvc.perform(delete("/v1/usuarios/{id}", existingId)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteRetornarNotFoundQuandoIdNaoExistir() throws Exception {
        doThrow(ResourceNotFoundException.class)
                .when(usuarioService)
                .delete(notExistingId);

        ResultActions result =
                mockMvc.perform(delete("/v1/usuarios/{id}", notExistingId)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }
}
