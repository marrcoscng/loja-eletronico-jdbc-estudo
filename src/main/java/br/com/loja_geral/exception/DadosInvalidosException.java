package br.com.loja_geral.exception;

public class DadosInvalidosException extends RuntimeException{

    public DadosInvalidosException(String msg){
        super(msg);
    }
}
