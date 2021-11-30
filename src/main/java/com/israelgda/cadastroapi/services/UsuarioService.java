package com.israelgda.cadastroapi.services;

import com.israelgda.cadastroapi.dto.DadosDTO;
import com.israelgda.cadastroapi.dto.EnderecoDTO;
import com.israelgda.cadastroapi.dto.UsuarioDTO;
import com.israelgda.cadastroapi.entities.Usuario;
import com.israelgda.cadastroapi.repositories.UsuarioRepository;
import com.israelgda.cadastroapi.services.clients.CepApiClient;
import com.israelgda.cadastroapi.services.exceptions.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.ConstraintViolationException;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioDTO findByCpf(String cpf){
        Usuario usuario = usuarioRepository.findByCpf(cpf)
                .orElseThrow(()-> new ResourceNotFoundException("Entity Not Found for Document Number: " + cpf));;
        return new UsuarioDTO(usuario);
    }

    public UsuarioDTO create(DadosDTO dadosDTO) throws PostalCodeNotFound {
        try {
            EnderecoDTO enderecoDTO = CepApiClient.searchAdress(dadosDTO);
            Usuario usuario = dtoToEntity(dadosDTO, enderecoDTO);

            usuario = usuarioRepository.save(usuario);
            return  new UsuarioDTO(usuario);
        } catch (HttpClientErrorException e){
            throw new PostalCodeInvalidFormatException("Formato incorreto de CEP! Verifique o número informado.");
        } catch (PostalCodeNotFound e){
            throw new PostalCodeNotFound("CEP não encontrado! Verifique o número informado.");
        } catch (ConstraintViolationException e){
            throw new DataFormatViolationException("Violação de formato! Verifique os dados informados e insira corretamente.");
        } catch (DataIntegrityViolationException e){
            throw new CpfAlredyRegistered("Este CPF já encontra-se cadastrado, não é possível utilizar o mesmo.");
        }
    }

    private Usuario dtoToEntity(DadosDTO dadosDTO, EnderecoDTO enderecoDTO) {
        Usuario usuario = new Usuario();
        usuario.setId(null);
        usuario.setNome(dadosDTO.getNome());
        usuario.setDataNascimento(dadosDTO.getDataNascimento());
        usuario.setCidade(enderecoDTO.getLocalidade());
        usuario.setBairro(enderecoDTO.getBairro());
        usuario.setEstado(enderecoDTO.getUf());
        usuario.setCpf(dadosDTO.getCpf());
        usuario.setTelefone(dadosDTO.getTelefone());

        return usuario;
    }
}
