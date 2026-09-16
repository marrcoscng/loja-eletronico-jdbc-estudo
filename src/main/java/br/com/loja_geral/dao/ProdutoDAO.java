package br.com.loja_geral.dao;

import br.com.loja_geral.model.Produto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProdutoDAO<T extends Produto> {

    void cadastrarProduto(T produto) throws SQLException;
    ArrayList<T> buscarProdutos() throws SQLException;
    T buscarProdutoId(Long id) throws SQLException;
    void deletarProdutoId(Long id) throws SQLException;
    void editarProduto(T produto) throws SQLException;
    boolean validarProduto(Produto produto) throws SQLException;
    boolean procurarProdutoPorId(Long id) throws SQLException;

}
