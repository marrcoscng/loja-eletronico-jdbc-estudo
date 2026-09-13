package br.com.loja_geral.service;

import br.com.loja_geral.dao.DAOFactory;
import br.com.loja_geral.dao.ProdutoDAO;
import br.com.loja_geral.model.Produto;
import br.com.loja_geral.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class ServiceProduto {

    public void salvarProduto(Produto produto){

        // Iniciamos a connection pelo pacote Service. Dentro de um try-resources-catch para que ela seja fechada assim que acabar com o procedimento necessario.
        try(Connection conn = DBConnection.getConnection()){

            ProdutoDAO produtoDAOJDBC = DAOFactory.getProdutoDAO(conn);
            if(produtoDAOJDBC.validarProduto(produto)){
                produtoDAOJDBC.cadastrarProduto(produto);
            }
        }catch(SQLException e){
            System.err.println("Erro ao tentar salvar produto - "+e.getMessage());
        }
    }

}
