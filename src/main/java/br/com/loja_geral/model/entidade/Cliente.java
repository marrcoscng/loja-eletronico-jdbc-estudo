package br.com.loja_geral.model.entidade;

public class Cliente extends Usuario{

    Endereco endereco;

    public Cliente(String nome, String cpf, Email email, String senha, Endereco endereco){
        super(nome,cpf,email,senha);
        this.endereco = endereco;
    }


}
