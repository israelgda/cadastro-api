package com.israelgda.cadastroapi.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name="tb_usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotNull
    @Column(nullable = false)
    @Size(min = 3, max = 100)
    private String nome;

    @NotNull
    @Column(name= "datanascimento", nullable = false)
    private String dataNascimento;

    private String cidade;
    private String bairro;
    private String estado;

    @NotNull
    @Column(unique = true)
    @Size(min = 11, max = 11)
    private String cpf;

    @Size(max = 11)
    private String telefone;

    public Usuario(){

    }
}
