package br.com.loja_geral.model;

import br.com.loja_geral.model.enums.Categoria;

import java.math.BigDecimal;

public class Produto {

    private Long id;

    private String nome;
    private String descricao;
    private String PATH_FILE;
    private BigDecimal preco;
    private Categoria categoria;

    public Produto(String nome, String descricao, String PATH_FILE, BigDecimal preco, Categoria categoria){
        this.nome = nome;
        this.descricao = descricao;
        this.PATH_FILE  =PATH_FILE;
        this.preco = preco;
        this.categoria = categoria;
    }

    public String getPATH_FILE(){
        return PATH_FILE;
    }

    public String toString(){
        return String.format(
                "Nome: %s%n"+
                "Descrição: %s%n"+
                "Preço: %.2f%n"+
                "Categoria: %s%n",nome,descricao,preco,categoria);
    }

    public Long getId(){
        return id;
    }
    public BigDecimal getPreco(){
        return preco;
    }

}
