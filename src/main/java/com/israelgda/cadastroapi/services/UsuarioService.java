package com.israelgda.cadastroapi.services;

import com.israelgda.cadastroapi.dto.DadosDTO;
import com.israelgda.cadastroapi.dto.EnderecoDTO;
import com.israelgda.cadastroapi.dto.UsuarioDTO;
import com.israelgda.cadastroapi.entities.Usuario;
import com.israelgda.cadastroapi.repositories.UsuarioRepository;
import com.israelgda.cadastroapi.services.clients.CepApiClient;
import com.israelgda.cadastroapi.services.exceptions.CpfAlredyRegistered;
import com.israelgda.cadastroapi.services.exceptions.PostalCodeInvalidFormatException;
import com.israelgda.cadastroapi.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.ConstraintViolationException;

import static com.israelgda.cadastroapi.utils.VerificadorDataUtil.verificaData;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    private Usuario findById(Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Entity Not Found for Id: " + id));

        return usuario;
    }

    @Transactional(readOnly = true)
    public UsuarioDTO findByCpf(String cpf){
        Usuario usuario = usuarioRepository.findByCpf(cpf)
                .orElseThrow(()-> new ResourceNotFoundException("Entity Not Found for Document Number: " + cpf));
        return new UsuarioDTO(usuario);
    }

    @Transactional
    public UsuarioDTO create(DadosDTO dadosDTO) {
        try {
            EnderecoDTO enderecoDTO = CepApiClient.searchAdress(dadosDTO);
            Usuario usuario = dtoToEntity(dadosDTO, enderecoDTO);

            usuario = usuarioRepository.save(usuario);
            return  new UsuarioDTO(usuario);
        } catch (HttpClientErrorException e){
            throw new PostalCodeInvalidFormatException("Formato incorreto de CEP! Verifique o número informado.");
        } catch (ConstraintViolationException e){
            throw new CpfAlredyRegistered("Este CPF já encontra-se cadastrado, não é possível utilizar o mesmo.");
        }
    }

    @Transactional
    public UsuarioDTO update(Long id, UsuarioDTO usuarioDTO) {
        try {
            Usuario usuarioAtualizado = findById(id);
            usuarioAtualizado = usuarioRepository.save(updateCadastro(usuarioAtualizado, usuarioDTO));

            return new UsuarioDTO(usuarioAtualizado);
        } catch (HttpClientErrorException e){
            throw new PostalCodeInvalidFormatException("Formato incorreto de CEP! Verifique o número informado.");
        } catch (DataIntegrityViolationException e){
            throw new CpfAlredyRegistered("Este CPF já encontra-se cadastrado, não é possível utilizar o mesmo.");
        }
    }

    public void delete(Long id) {
        try {
            usuarioRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Entity Not Found for Id: " + id);
        }
    }

    private Usuario dtoToEntity(DadosDTO dadosDTO, EnderecoDTO enderecoDTO) {
        Usuario usuario = new Usuario();
        usuario.setId(null);
        usuario.setNome(dadosDTO.getNome());
        usuario.setDataNascimento(verificaData(dadosDTO.getDataNascimento()));
        usuario.setCidade(enderecoDTO.getLocalidade());
        usuario.setBairro(enderecoDTO.getBairro());
        usuario.setEstado(enderecoDTO.getUf());
        usuario.setCpf(dadosDTO.getCpf());
        usuario.setTelefone(dadosDTO.getTelefone());

        return usuario;
    }

    private Usuario updateCadastro(Usuario usuarioAtualizado, UsuarioDTO usuarioDTO) {
        usuarioAtualizado.setNome(usuarioDTO.getNome());
        usuarioAtualizado.setDataNascimento(verificaData(usuarioDTO.getDataNascimento()));
        usuarioAtualizado.setCidade(usuarioDTO.getCidade());
        usuarioAtualizado.setBairro(usuarioDTO.getBairro());
        usuarioAtualizado.setEstado(usuarioDTO.getEstado());
        usuarioAtualizado.setCpf(usuarioDTO.getCpf());
        usuarioAtualizado.setTelefone(usuarioDTO.getTelefone());

        return usuarioAtualizado;
    }

}
