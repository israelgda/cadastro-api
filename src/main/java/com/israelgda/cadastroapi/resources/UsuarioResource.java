package com.israelgda.cadastroapi.resources;

import com.israelgda.cadastroapi.dto.DadosDTO;
import com.israelgda.cadastroapi.dto.UsuarioDTO;
import com.israelgda.cadastroapi.services.UsuarioService;
import com.israelgda.cadastroapi.services.exceptions.PostalCodeNotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value="/v1/usuarios")
public class UsuarioResource {

    private final UsuarioService usuarioService;

    public UsuarioResource(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping(value="/{cpf}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable String cpf){
        UsuarioDTO usuarioDTO = usuarioService.findByCpf(cpf);
        return ResponseEntity.ok().body(usuarioDTO);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@RequestBody DadosDTO dadosDTO) throws PostalCodeNotFound {
        UsuarioDTO usuarioDTO = usuarioService.create(dadosDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuarioDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(usuarioDTO);
    }
}
