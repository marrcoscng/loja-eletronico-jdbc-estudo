package br.com.loja_geral.controller;

import br.com.loja_geral.model.Produto;
import br.com.loja_geral.service.ServiceProduto;

import java.math.BigDecimal;

public class ProdutoController {

    public void salvarProduto(String nome, String descricao, String PATH_FILE, BigDecimal preco){
        Produto produto = new Produto(nome,descricao,PATH_FILE,preco);
        ServiceProduto serviceProduto = new ServiceProduto();
        serviceProduto.salvarProduto(produto);
    }

}
