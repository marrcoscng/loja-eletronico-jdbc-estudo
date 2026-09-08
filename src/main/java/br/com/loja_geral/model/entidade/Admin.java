package br.com.loja_geral.model.entidade;

import br.com.loja_geral.model.entidade.enums.TipoDoUsuario;

public class Admin extends Usuario{

    public Admin(String nome, String cpf, Email email, String senha){
        super(nome,cpf,email,senha, TipoDoUsuario.ADMIN);
    }
}
