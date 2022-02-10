package com.israelgda.cadastroapi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    private String nome;

    @NotNull
    @Column(name= "datanascimento", nullable = false)
    private String dataNascimento;

    private String cidade;
    private String bairro;
    private String estado;

    @NotNull
    @Column(unique = true)
    private String cpf;

    @Column(nullable = true)
    private String telefone;

    public Usuario(){

    }
}
