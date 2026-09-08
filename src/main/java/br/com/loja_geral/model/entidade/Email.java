package br.com.loja_geral.model.entidade;

import br.com.loja_geral.exception.EmailException;
import java.util.regex.Pattern;


public class Email {

    private static final String enderecoPadrao = "^[A-Za-z0-9_.-]+@[A-Za-z0-9]*\\.[A-Za-z]{2,}$";

    private String endereco;

    public Email(String endereco, String senha){
        regraEmail(endereco);
        this.endereco = endereco;
    }

    public String getEndereco(){
        return endereco;
    }

    private void regraEmail(String endereco){
        if(endereco == null || endereco.isBlank()){
            throw new EmailException("E-mail não pode ser nulo");
        }
        if(!Pattern.matches(enderecoPadrao,endereco)){
            throw new EmailException("Formato de E-mail inválido");
        }
    }
}
