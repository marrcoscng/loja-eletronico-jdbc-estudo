package br.com.loja_geral.model;

import br.com.loja_geral.model.enums.TipoDoUsuario;

public class Gerente extends Usuario{

    public Gerente(String nome, String cpf, Email email, String senha){
        super(nome,cpf,email,senha, TipoDoUsuario.GERENTE);
    }
}
