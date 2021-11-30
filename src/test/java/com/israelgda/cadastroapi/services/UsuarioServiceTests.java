package com.israelgda.cadastroapi.services;

import com.israelgda.cadastroapi.dto.DadosDTO;
import com.israelgda.cadastroapi.dto.UsuarioDTO;
import com.israelgda.cadastroapi.entities.Usuario;
import com.israelgda.cadastroapi.constants.Constants;
import com.israelgda.cadastroapi.repositories.UsuarioRepository;
import com.israelgda.cadastroapi.services.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UsuarioServiceTests {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    private String existingDocument;
    private String nonExistingDocument;
    private Usuario usuario;
    private DadosDTO dadosDTO;
    private DadosDTO dadosDTOCepInexistente;
    private DadosDTO dadosDTOCepInvalido;
    private DadosDTO dadosDTOCpfExistente;

    @BeforeEach
    void setUp(){
        existingDocument = "35467895";
        nonExistingDocument = "99999999";
        usuario = Constants.createUsuario();
        dadosDTO = Constants.createDadosDTO();
    }

    @Test
    public void findByCpfDeveRetornarUsuarioDtoQuandoDocumentoExistir(){
        when(usuarioRepository
                .findByCpf(existingDocument))
                .thenReturn(Optional.of(usuario));

        UsuarioDTO usuarioDTO = usuarioService.findByCpf(existingDocument);

        assertNotNull(usuarioDTO);
        assertEquals("Israel", usuarioDTO.getNome());
        assertEquals("08/05/1997", usuarioDTO.getDataNascimento());
        assertEquals("11211311499", usuarioDTO.getCpf());
        assertEquals("82981110888", usuarioDTO.getTelefone());
        assertEquals("Maceió", usuarioDTO.getCidade());
        assertEquals("Serraria", usuarioDTO.getBairro());
        assertEquals("AL", usuarioDTO.getEstado());

        verify(usuarioRepository, times(1)).findByCpf(existingDocument);
    }

    @Test
    public void findByCpfDeveLancarResourceNotFoundExceptionQuandoDocumentoNaoExistir(){
        when(usuarioRepository
                .findByCpf(nonExistingDocument))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, ()-> {
            UsuarioDTO usuarioDTO = usuarioService.findByCpf(nonExistingDocument);
        });

        verify(usuarioRepository, times(1)).findByCpf(nonExistingDocument);
    }

    @Test
    public void createDeveRetornarUsuarioQuandoCepForValido() throws PostalCodeNotFound {
        when(usuarioRepository
                .save(ArgumentMatchers.any()))
                .thenReturn(usuario);

        UsuarioDTO usuarioDTO = usuarioService.create(dadosDTO);

        assertNotNull(usuarioDTO);
        assertEquals("Israel", usuarioDTO.getNome());
        assertEquals("08/05/1997", usuarioDTO.getDataNascimento());
        assertEquals("11211311499", usuarioDTO.getCpf());
        assertEquals("82981110888", usuarioDTO.getTelefone());
        assertEquals("Maceió", usuarioDTO.getCidade());
        assertEquals("Serraria", usuarioDTO.getBairro());
        assertEquals("AL", usuarioDTO.getEstado());
    }

    @Test
    public void createDeveLancarPostalCodeNotFoundQuandoCepForNaoForEncontrado() throws PostalCodeNotFound {
        assertThrows(PostalCodeNotFound.class, ()->{
            dadosDTOCepInexistente = Constants.createDadosDTOCepInexistente();
            UsuarioDTO usuarioDTO = usuarioService.create(dadosDTOCepInexistente);
        });
    }

    @Test
    public void createDeveLancarPostalCodeInvalidFormatQuandoCepForInvalido() throws PostalCodeNotFound {
        assertThrows(PostalCodeInvalidFormatException.class, ()->{
            dadosDTOCepInvalido = Constants.createDadosDTOCepInvalido();
            UsuarioDTO usuarioDTO = usuarioService.create(dadosDTOCepInvalido);
        });
    }

    @Test
    public void createDeveLancarCpfAlredyRegisteredQuandoCpfJáExistir() throws PostalCodeNotFound {
        when(usuarioRepository.save(any()))
                .thenThrow(CpfAlredyRegistered.class);

        assertThrows(CpfAlredyRegistered.class, ()->{
            UsuarioDTO usuarioDTO = usuarioService.create(dadosDTO);
        });
    }

    @Test
    public void createDeveLancarDataFormatViolationQuandoDadosForemIncorretos() throws PostalCodeNotFound {
        when(usuarioRepository.save(any()))
                .thenThrow(DataFormatViolationException.class);

        assertThrows(DataFormatViolationException.class, ()->{
            UsuarioDTO usuarioDTO = usuarioService.create(dadosDTO);
        });
    }

    @Test
    public void createDeveLancarBirthDateInvalidFormatQuandoDatadeNascimentoForInvalida() throws PostalCodeNotFound {
        when(usuarioRepository.save(any()))
                .thenThrow(BirthDateInvalidFormatException.class);

        assertThrows(BirthDateInvalidFormatException.class, ()->{
            UsuarioDTO usuarioDTO = usuarioService.create(dadosDTO);
        });
    }
}
