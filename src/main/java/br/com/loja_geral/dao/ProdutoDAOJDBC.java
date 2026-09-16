package br.com.loja_geral.dao;

import br.com.loja_geral.exception.ProdutoNaoEncontradoException;
import br.com.loja_geral.model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutoDAOJDBC implements ProdutoDAO<Produto>{

    private Connection conn = null;

    public ProdutoDAOJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void cadastrarProduto(Produto produto) throws SQLException{
        String inserirBD = "INSERT INTO produto (nome,descricao,path_file,preco) VALUES (?,?,?,?);";
        try (PreparedStatement preparedStatement = conn.prepareStatement(inserirBD,PreparedStatement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1,produto.getNome());
            preparedStatement.setString(2,produto.getDescricao());
            preparedStatement.setString(3,produto.getPATH_FILE());
            preparedStatement.setBigDecimal(4,produto.getPreco());

            preparedStatement.executeUpdate();

            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                if(resultSet.next()){
                    Long idProduto = resultSet.getLong(1);
                    produto.setId(idProduto);
                }
            }
        }
    }

    @Override
    public ArrayList<Produto> buscarProdutos()  throws SQLException{
        ArrayList<Produto> listaProdutos = new ArrayList<>();

        try(PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM produto;")){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    Produto produto = new Produto(resultSet.getString("nome"),resultSet.getString("descricao"),resultSet.getString("path_file"),resultSet.getBigDecimal("preco"));
                    produto.setId(resultSet.getLong("id"));
                    listaProdutos.add(produto);
                }
            }
            return listaProdutos;
        }
    }

    @Override
    public Produto buscarProdutoId(Long id) throws SQLException {
        String buscarProduto = "SELECT * FROM produto WHERE id = ?";
        try(PreparedStatement preparedStatement = conn.prepareStatement(buscarProduto);){
            preparedStatement.setLong(1,id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Produto produto = new Produto(resultSet.getString("nome"), resultSet.getString("descricao"), resultSet.getString("path_file"), resultSet.getBigDecimal("preco"));
                    produto.setId(resultSet.getLong("id"));
                    return produto;
                } else {
                    throw new ProdutoNaoEncontradoException("Produto não encontrado");
                }
            }
        }
    }

    @Override
    public void deletarProdutoId(Long id) throws SQLException {
        try(PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM produto WHERE id = ?;")){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        }
    }


    @Override
    public boolean procurarProdutoPorId(Long id) throws SQLException{
        try(PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM produto WHERE id = ?;")){
            preparedStatement.setLong(1,id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                return resultSet.next();
            }
        }
    }


    @Override
    public void editarProduto(Produto produto) throws SQLException {
        String alterarProduto = "UPDATE produto " +
                "SET nome = ?, " +
                "descricao = ?, " +
                "path_file = ?, " +
                "preco = ?, "+
                "WHERE id = ?;";
        try(PreparedStatement preparedStatement = conn.prepareStatement(alterarProduto)){
            preparedStatement.setString(1,produto.getNome());
            preparedStatement.setString(2,produto.getDescricao());
            preparedStatement.setString(3,produto.getPATH_FILE());
            preparedStatement.setBigDecimal(4,produto.getPreco());
            preparedStatement.setLong(5,produto.getId());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public boolean validarProduto(Produto produto) throws SQLException {
        String buscaProduto = "SELECT p.nome FROM produto p WHERE p.nome = ?";
        try(PreparedStatement preparedStatement = conn.prepareStatement(buscaProduto)){
            preparedStatement.setString(1,produto.getNome());
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }


}
