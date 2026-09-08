package br.com.loja_geral.model.entidade.dao;

import br.com.loja_geral.model.entidade.Produto;
import java.util.ArrayList;

public interface ProdutoDAO<T extends Produto> {

    void cadastrarProduto(T produto);
    ArrayList<T> buscarProdutos();
    void buscarProdutoId(Long id);
    void deletarProdutoId(Long id);
    T editarProdutoId(Long id);

}
