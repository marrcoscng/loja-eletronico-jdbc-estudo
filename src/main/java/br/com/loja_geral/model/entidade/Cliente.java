package br.com.loja_geral.model.entidade;

import br.com.loja_geral.model.entidade.enums.TipoDoUsuario;

public class Cliente extends Usuario{

    Endereco endereco;

    public Cliente(String nome, String cpf, Email email, String senha){
        super(nome,cpf,email,senha, TipoDoUsuario.CLIENTE);
    }

    public void setEndereco(Endereco endereco){
        this.endereco = endereco;
    }



}
