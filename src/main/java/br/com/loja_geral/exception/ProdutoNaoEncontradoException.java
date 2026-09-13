package br.com.loja_geral.exception;

public class ProdutoNaoEncontradoException extends RuntimeException{

    public ProdutoNaoEncontradoException(String msg){
        super(msg);
    }
}
