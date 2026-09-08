package br.com.loja_geral.exception;

import br.com.loja_geral.model.entidade.Email;

public class EmailException extends RuntimeException{

    public EmailException(String msg){
        super(msg);
    }

}
