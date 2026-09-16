package br.com.loja_geral.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DAOFactory {

    public static LoginDAO getLoginDAO(Connection conn){
        return new LoginDAOJDBC(conn);
    }

    public static ProdutoDAO getProdutoDAO(Connection conn){
        return new ProdutoDAOJDBC(conn);
    }

    public static PedidoDAO getPedidoDAO(Connection conn) {
        return new PedidoDAOJDBC(conn);
    }

}
