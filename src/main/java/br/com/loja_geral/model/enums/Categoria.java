package br.com.loja_geral.model.enums;

public enum Categoria {

    PROMOCAO("Promoção"),
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
