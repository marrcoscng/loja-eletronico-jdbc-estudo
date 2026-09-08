package br.com.loja_geral.exception;

public class UsuarioNaoEncontradoException extends RuntimeException{

    public UsuarioNaoEncontradoException(String msg){
        super(msg);
    }
}
