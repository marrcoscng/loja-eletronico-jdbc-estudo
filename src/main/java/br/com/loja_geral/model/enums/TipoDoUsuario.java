package br.com.loja_geral.model.enums;

public enum TipoDoUsuario {

    CLIENTE("CLIENTE"),
    GERENTE("GERENTE"),
    ADMIN("ADMIN");

    private String tipo;

    TipoDoUsuario(String tipo){
        this.tipo = tipo;
    }

    public String getNome(){
        return tipo;
    }
}
