package br.com.loja_geral.service;

import br.com.loja_geral.dao.DAOFactory;
import br.com.loja_geral.dao.LoginDAO;
import br.com.loja_geral.exception.DBException;
import br.com.loja_geral.model.Usuario;
import br.com.loja_geral.util.DBConnection;
import br.com.loja_geral.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class ServiceLogin {

    public void serviceCadastro(Usuario user){

        try(Connection conn = DBConnection.getConnection()){
            LoginDAO loginDAO = DAOFactory.getLoginDAO(conn);
            // add regras de negocio como a negacao de e-mail e cpf duplicados ou já cadastrados ->
            loginDAO.cadastrar(user);

        }catch(SQLException e){
            throw new DBException("Erro - "+e.getMessage());
        }
    }


}
