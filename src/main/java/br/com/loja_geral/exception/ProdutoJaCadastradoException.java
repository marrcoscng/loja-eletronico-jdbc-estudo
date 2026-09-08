package br.com.loja_geral.exception;

public class ProdutoJaCadastradoException extends RuntimeException{

    public ProdutoJaCadastradoException(String msg){
        super(msg);
    }
}
