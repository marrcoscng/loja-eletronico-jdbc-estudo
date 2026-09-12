package br.com.loja_geral.dao;

import java.sql.Connection;

public class DAOFactory {

    public static LoginDAO getLoginDAO(Connection conn){
        return new LoginDAOJDBC(conn);
    }

    public static ProdutoDAO getProdutoDAO(Connection conn){
        return new ProdutoDAOJDBC(conn);
    }

}
