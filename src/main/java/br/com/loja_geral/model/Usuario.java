package br.com.loja_geral.model;

import br.com.loja_geral.exception.DadosInvalidosException;
import br.com.loja_geral.model.enums.TipoDoUsuario;
import br.com.loja_geral.util.ValidadorCpf;

public abstract class Usuario {

    private Long id;

    private String nome;
    private String cpf;
    private Email email;
    private String senha;
    private TipoDoUsuario tipoDoUsuario;


    /**
     * @param nome;
     * @param cpf;
     * @param email;
     * @param senha;
     * @param tipoDoUsuario;
     * @throws br.com.loja_geral.exception.DocumentoInvalidoException;
     *
     */
    public Usuario(String nome, String cpf, Email email, String senha, TipoDoUsuario tipoDoUsuario){
        setCpf(cpf);
        setNome(nome);
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

    private void setCpf(String cpf){
        ValidadorCpf.validadorCpf(cpf);
        this.cpf = cpf;
    }
    private void setNome(String nome){
        if(nome == null || nome.isBlank()){
            throw new DadosInvalidosException("Nome vazio");
        }
       this.nome = nome;
    }




}
