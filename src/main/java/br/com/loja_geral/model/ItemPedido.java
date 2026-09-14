package br.com.loja_geral.model;

import java.math.BigDecimal;

public class ItemPedido {

    private Produto produto;
    private Integer quantidade;

    public ItemPedido(Produto produto, Integer quantidade){
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public BigDecimal getSubtotal(){
        return produto.getPreco().multiply(BigDecimal.valueOf(quantidade));
    }
    public Integer getQuantidade(){
        return quantidade;
    }
    public Produto getProduto(){
        return produto;
    }

    public void adicionarQuantidade(int quantidade){
        this.quantidade += quantidade;
    }

    public void setQuantidade(int quantidade){
        this.quantidade = quantidade;
    }
}
