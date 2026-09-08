package br.com.loja_geral.model.entidade;

import br.com.loja_geral.model.entidade.Email;

public abstract class Usuario {

    private Long id;

    private String nome;
    private String cpf;
    private Email email;
    private String senha;


    public Usuario(String nome, String cpf, Email email, String senha){
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
    }

    public void setId(Long id){
        this.id = id;
    }




}
