package com.israelgda.cadastroapi.services.clients;

import com.israelgda.cadastroapi.dto.DadosDTO;
import com.israelgda.cadastroapi.dto.EnderecoDTO;
import com.israelgda.cadastroapi.services.exceptions.PostalCodeNotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class CepApiClient {

    private static RestTemplate templateApiCep = new RestTemplate();

    private static UriComponents uri;

    public static EnderecoDTO searchAdress(DadosDTO dadosDTO) throws PostalCodeNotFound {

            uri = UriComponentsBuilder.newInstance()
                    .scheme("https")
                    .host("viacep.com.br")
                    .path("ws/"+dadosDTO.getCep()+"/json")
                    .build();

            ResponseEntity<EnderecoDTO> endereco = templateApiCep.getForEntity(uri.toUriString(), EnderecoDTO.class);

            if(endereco.getBody().getErro() == "true"){
               throw new PostalCodeNotFound("");
            }

            EnderecoDTO enderecoDTO = EnderecoDTO.builder()
                                    .localidade(endereco.getBody().getLocalidade())
                                    .uf(endereco.getBody().getUf())
                                    .bairro(endereco.getBody().getBairro()).build();

            return enderecoDTO;
    }
}
