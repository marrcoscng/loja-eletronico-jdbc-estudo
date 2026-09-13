package br.com.loja_geral.dao;

import br.com.loja_geral.exception.DBException;
import br.com.loja_geral.exception.ProdutoJaCadastradoException;
import br.com.loja_geral.exception.ProdutoNaoEncontradoException;
import br.com.loja_geral.model.Produto;
import com.google.protobuf.DescriptorProtos;

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
    public void cadastrarProduto(Produto produto){
        String inserirBD = "INSERT INTO produto (nome,descricao,path_file,preco) VALUES (?,?,?,?);";
        try (PreparedStatement preparedStatement = conn.prepareStatement(inserirBD)){
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
        }catch(SQLException e){
            throw new DBException("Error on Query - "+e.getMessage());

        }
    }

    @Override
    public Produto buscarProdutoId(Long id) {
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
        }catch(SQLException e){
            throw new DBException("Error em sql - "+e.getMessage());
        }
    }

    @Override
    public void deletarProdutoId(Long id) {
        try(PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM produto WHERE id = ?;")){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            throw  new DBException("Erro ao injetar SQL - "+e.getMessage());
        }
    }


    @Override
    public boolean procurarProdutoPorId(Long id){
        try(PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM produto WHERE id = ?;")){
            preparedStatement.setLong(1,id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                return resultSet.next();
            }
        }catch(SQLException e){
            throw new DBException("Error na Query - "+e.getMessage());
        }
    }


    @Override
    public void editarProduto(Produto produto) {
        String alterarProduto = "UPDATE produto " +
                "SET nome = ?, " +
                "descricao = ? " +
                "path_file = ? " +
                "preco = ? "+
                "WHERE id = ?";
        try(PreparedStatement preparedStatement = conn.prepareStatement(alterarProduto)){
            preparedStatement.setString(1,produto.getNome());
            preparedStatement.setString(2,produto.getDescricao());
            preparedStatement.setString(3,produto.getPATH_FILE());
            preparedStatement.setBigDecimal(4,produto.getPreco());
            preparedStatement.setLong(5,produto.getId());

            preparedStatement.executeUpdate();
        }catch(SQLException e){
            throw new DBException("Error update product - "+e.getMessage());
        }

    }

    @Override
    public boolean validarProduto(Produto produto) {
        String buscaProduto = "SELECT p.nome FROM produto p WHERE p.nome = ?";
        try(PreparedStatement preparedStatement = conn.prepareStatement(buscaProduto)){
            preparedStatement.setString(1,produto.getNome());
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }catch(SQLException e ){
            throw new DBException("Erro ao gerar ResultSet - "+e.getMessage());
        }
    }


}
