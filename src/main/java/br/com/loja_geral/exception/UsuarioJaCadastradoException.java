package br.com.loja_geral.exception;

public class UsuarioJaCadastradoException extends RuntimeException{

    public UsuarioJaCadastradoException(String msg){
        super(msg);
    }
}
