package br.com.loja_geral.service;

import br.com.loja_geral.dao.DAOFactory;
import br.com.loja_geral.dao.ProdutoDAO;
import br.com.loja_geral.exception.DBException;
import br.com.loja_geral.exception.ProdutoJaCadastradoException;
import br.com.loja_geral.model.Produto;
import br.com.loja_geral.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class ServiceProduto {

    public void salvarProduto(Produto produto){

        // Iniciamos a connection pelo pacote Service. Dentro de um try-resources-catch para que ela seja fechada assim que acabar com o procedimento necessario.
        try(Connection conn = DBConnection.getConnection()){
            try {
                conn.setAutoCommit(false);
                ProdutoDAO produtoDAOJDBC = DAOFactory.getProdutoDAO(conn);
                if (produtoDAOJDBC.validarProduto(produto)) {
                    throw new ProdutoJaCadastradoException("Produto já cadastrado");
                }
                produtoDAOJDBC.cadastrarProduto(produto);
                conn.commit();
            }catch(SQLException er){
                try {
                    conn.rollback();
                    throw new DBException("Erro ao salvar pedido - " + er.getMessage());
                }catch(SQLException rollback){
                    throw new DBException("Erro ao inicializar rollback - "+rollback.getMessage());
                }
            }
        }catch(SQLException e){
            throw new DBException("Erro ao conectar banco de dados - "+e.getMessage());
        }
    }

    public void deletarProdutoPorId(Long id){
        try(Connection conn = DBConnection.getConnection()){
            try {
                conn.setAutoCommit(false);
                ProdutoDAO produtoDAO = DAOFactory.getProdutoDAO(conn);

                if (!produtoDAO.procurarProdutoPorId(id)) {
                    throw new ProdutoJaCadastradoException("Produto não encontrado no sistema!");
                }
                produtoDAO.deletarProdutoId(id);
                conn.commit();
            }catch(SQLException rollback){
                try {
                    conn.rollback();
                    throw new DBException("Produto não deletado -- rollback atvado " + rollback.getMessage());
                }catch(SQLException er){
                    throw new DBException("Erro ao chamar rollback - "+er.getMessage());
                }
            }
        }catch(SQLException e){
            throw new DBException("Erro ao conversar com Banco de dados - "+e.getMessage());
        }
    }

}
