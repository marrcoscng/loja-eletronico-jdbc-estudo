package br.com.loja_geral.service;

import br.com.loja_geral.dao.DAOFactory;
import br.com.loja_geral.dao.LoginDAO;
import br.com.loja_geral.exception.DBException;
import br.com.loja_geral.exception.UsuarioJaCadastradoException;
import br.com.loja_geral.model.Usuario;
import br.com.loja_geral.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class ServiceLogin {

    public void serviceCadastro(Usuario user){

        try(Connection conn = DBConnection.getConnection()){
            LoginDAO loginDAO = DAOFactory.getLoginDAO(conn);
            // regra de negócio para validar existêncai de usuário no banco de dados
            if(loginDAO.validarSeExistePorEmailOuCpf(user)){
                throw new UsuarioJaCadastradoException("Dados incorretos!");
            }
            loginDAO.cadastrar(user);
        }catch(SQLException e){
            throw new DBException("Erro - "+e.getMessage());
        }
    }

    public Usuario autenticar(String email,String senha){
        try(Connection conn = DBConnection.getConnection()){
            LoginDAO loginDAO = DAOFactory.getLoginDAO(conn);
            return loginDAO.autenticar(email,senha);
        }catch(SQLException e){
            throw new DBException("Erro ao consultar DB "+e.getMessage());
        }
    }


}
