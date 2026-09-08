package br.com.loja_geral.exception;

public class EmailExistenteException extends RuntimeException{

    public EmailExistenteException(String msg){
        super(msg);
    }
}
