package br.com.loja_geral.dto;

import br.com.loja_geral.model.Produto;

public class ProdutoEstoqueDTO extends Produto {
    private final int quantidadeEstoque;

    public ProdutoEstoqueDTO(Produto produto, int quantidadeEstoque, Long id) {
        super(produto.getNome(), produto.getDescricao(), produto.getPATH_FILE(), produto.getPreco());
        this.setId(id);
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

}