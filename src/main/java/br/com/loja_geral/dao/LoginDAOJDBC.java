package br.com.loja_geral.dao;

import br.com.loja_geral.exception.DBException;
import br.com.loja_geral.exception.SenhaInvalidaException;
import br.com.loja_geral.exception.UsuarioNaoEncontradoException;
import br.com.loja_geral.model.*;
import br.com.loja_geral.model.enums.TipoDoUsuario;
import br.com.loja_geral.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAOJDBC implements LoginDAO<Usuario> {

    private Connection conn = null;

    public LoginDAOJDBC(Connection conn){
        this.conn = conn;
    }

    /**
     * @param usuario;
     * @throws DBException;
     */
    @Override
    public void cadastrar(Usuario usuario) {
        String inserirUsuario = "INSERT INTO usuario(nome,cpf,email,senha,tipo_do_usuario) VALUES (?,?,?,?,?)";
        try(PreparedStatement preparedStatement = conn.prepareStatement(inserirUsuario)){
            preparedStatement.setString(1,usuario.getNome());
            preparedStatement.setString(2,usuario.getCpf());
            preparedStatement.setString(3,usuario.getEmail().getEndereco());
            preparedStatement.setString(4,usuario.getSenha());
            preparedStatement.setString(5,usuario.getTipoDoUsuario().name());

            preparedStatement.executeUpdate();

        }catch(SQLException e){
            throw new DBException("Erro - "+e.getMessage());
        }
    }

    /**
     * @param email;
     * @param senha;
     * @throws UsuarioNaoEncontradoException;
     */
    @Override
    public Usuario autenticar(String email, String senha){
        String loginUsuario = "SELECT usr.nome, usr.cpf, usr.email, usr.senha, usr.tipo_do_usuario FROM usuario usr WHERE email = ?";
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(loginUsuario);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                TipoDoUsuario tipoDoUsuario = TipoDoUsuario.valueOf(resultSet.getString("tipo_do_usuario").toUpperCase());
                if(!senha.equals(resultSet.getString("senha"))){
                    throw new SenhaInvalidaException("Senha inválida!");
                }
                switch (tipoDoUsuario){
                    case ADMIN:
                        return new Admin(resultSet.getString("nome"),resultSet.getString("cpf"),new Email(resultSet.getString("email")),resultSet.getString("senha"));
                    case CLIENTE:
                        return new Cliente(resultSet.getString("nome"),resultSet.getString("cpf"),new Email(resultSet.getString("email")),resultSet.getString("senha"));
                    case GERENTE:
                        return new Gerente(resultSet.getString("nome"),resultSet.getString("cpf"),new Email(resultSet.getString("email")),resultSet.getString("senha"));
                }
            }
            throw new UsuarioNaoEncontradoException("usuario não encontrado");
        }catch(SQLException e){
            throw new DBException("Erro ao buscar usuário - "+e.getMessage());
        }
    }



}
