package br.com.loja_geral.exception;

public class DBException extends RuntimeException{

    public DBException(String msg){
        super(msg);
    }
}
