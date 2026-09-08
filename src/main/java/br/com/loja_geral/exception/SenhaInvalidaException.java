package br.com.loja_geral.exception;

public class SenhaInvalidaException extends RuntimeException{

    public SenhaInvalidaException(String msg){
        super(msg);
    }
}
