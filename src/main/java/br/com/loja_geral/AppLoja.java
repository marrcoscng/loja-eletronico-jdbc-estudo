package br.com.loja_geral;

import br.com.loja_geral.controller.ProdutoController;

import java.math.BigDecimal;


public class AppLoja {
    public static void main(String[] args) {

        ProdutoController produtoController = new ProdutoController();
        produtoController.salvarProduto("Notebook Asus Aspire 5","Notebook voltado para desempenho em jogos pesados",
                "exemplo/caminho/para/imagem.jpg",new BigDecimal("4689.99"));

    }
}





























