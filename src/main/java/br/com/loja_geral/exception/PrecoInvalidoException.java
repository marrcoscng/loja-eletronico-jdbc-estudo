package br.com.loja_geral.exception;

public class PrecoInvalidoException extends RuntimeException{

    public PrecoInvalidoException(String msg){
        super(msg);
    }
}
