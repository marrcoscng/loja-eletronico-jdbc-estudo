package br.com.loja_geral.exception;

public class DocumentoInvalidoException extends RuntimeException{

    public DocumentoInvalidoException(String msg){
        super(msg);
    }
}
