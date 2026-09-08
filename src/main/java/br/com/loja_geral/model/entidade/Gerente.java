package br.com.loja_geral.model.entidade;

import br.com.loja_geral.model.entidade.enums.TipoDoUsuario;

public class Gerente extends Usuario{

    public Gerente(String nome, String cpf, Email email, String senha){
        super(nome,cpf,email,senha, TipoDoUsuario.GERENTE);
    }
}
