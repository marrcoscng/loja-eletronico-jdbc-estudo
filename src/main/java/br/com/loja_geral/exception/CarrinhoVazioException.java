package br.com.loja_geral.exception;

public class CarrinhoVazioException extends RuntimeException{

    public CarrinhoVazioException(String msg){
        super(msg);
    }
}
