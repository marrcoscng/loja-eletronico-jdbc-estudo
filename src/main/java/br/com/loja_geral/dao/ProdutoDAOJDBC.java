package br.com.loja_geral.dao;

import br.com.loja_geral.exception.DBException;
import br.com.loja_geral.model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutoDAOJDBC implements ProdutoDAO<Produto>{

    private Connection conn = null;

    public ProdutoDAOJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void cadastrarProduto(Produto produto){
        String inserirBD = "INSERT INTO produto (nome,descricao,path_file,preco) VALUES (?,?,?,?);";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(inserirBD);
            preparedStatement.setString(1,produto.getNome());
            preparedStatement.setString(2,produto.getDescricao());
            preparedStatement.setString(3,produto.getPATH_FILE());
            preparedStatement.setBigDecimal(4,produto.getPreco());

            preparedStatement.executeUpdate();
        }catch(SQLException e){
            throw new DBException("Erro Query - "+e.getMessage());
        }
    }

    @Override
    public ArrayList<Produto> buscarProdutos() {
        return null;
    }

    @Override
    public void buscarProdutoId(Long id) {

    }

    @Override
    public void deletarProdutoId(Long id) {

    }

    @Override
    public Produto editarProdutoId(Long id) {
        return null;
    }


}
