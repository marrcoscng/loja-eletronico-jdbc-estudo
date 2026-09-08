package br.com.loja_geral.model.entidade;

import br.com.loja_geral.model.entidade.enums.TipoDoUsuario;

public abstract class Usuario {

    private Long id;

    private String nome;
    private String cpf;
    private Email email;
    private String senha;
    private TipoDoUsuario tipoDoUsuario;


    public Usuario(String nome, String cpf, Email email, String senha, TipoDoUsuario tipoDoUsuario){
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.tipoDoUsuario = tipoDoUsuario;

    }

    public void setId(Long id){
        this.id = id;
    }

    public String getNome(){
        return nome;
    }
    public String getCpf(){
        return cpf;
    }
    public String getSenha(){
        return senha;
    }
    public Email getEmail(){
        return email;
    }
    public void setTipo(TipoDoUsuario tipoDoUsuario){
        this.tipoDoUsuario = tipoDoUsuario;
    }
    public TipoDoUsuario getTipoDoUsuario(){
        return tipoDoUsuario;
    }



}
