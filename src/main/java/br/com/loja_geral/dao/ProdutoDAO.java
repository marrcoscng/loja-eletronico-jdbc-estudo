package br.com.loja_geral.dao;

import br.com.loja_geral.model.Produto;
import java.util.ArrayList;

public interface ProdutoDAO<T extends Produto> {

    void cadastrarProduto(T produto);
    ArrayList<T> buscarProdutos();
    T buscarProdutoId(Long id);
    void deletarProdutoId(Long id);
    void editarProduto(T produto);
    boolean validarProduto(Produto produto);

}
