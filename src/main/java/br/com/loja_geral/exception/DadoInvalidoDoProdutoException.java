package br.com.loja_geral.exception;

public class DadoInvalidoDoProdutoException extends RuntimeException{

    public DadoInvalidoDoProdutoException(String msg){
        super(msg);
    }
}
