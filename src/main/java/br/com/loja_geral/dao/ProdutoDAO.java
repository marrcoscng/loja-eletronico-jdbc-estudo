package br.com.loja_geral.dao;

import br.com.loja_geral.model.Produto;
import java.util.ArrayList;

public interface ProdutoDAO<T extends Produto> {

    void cadastrarProduto(T produto);
    ArrayList<T> buscarProdutos();
    void buscarProdutoId(Long id);
    void deletarProdutoId(Long id);
    T editarProdutoId(Long id);

}
