package br.com.loja_geral.model.entidade.enums;

public enum Categoria {

    ELETRONICO("Eletrônicos"),
    ROUPA("Roupas"),
    ELETRODOMESTICOS("Eletrodomesticos"),
    INFANTIL("Infantil");

    private final String categoria;

    Categoria(String categoria){
        this.categoria = categoria;
    }

    public String getCategoria(){
        return categoria;
    }
}
