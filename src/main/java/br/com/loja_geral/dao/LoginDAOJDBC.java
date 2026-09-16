package br.com.loja_geral.dao;

import br.com.loja_geral.exception.*;
import br.com.loja_geral.model.*;
import br.com.loja_geral.model.enums.TipoDoUsuario;

import java.sql.*;

public class LoginDAOJDBC implements LoginDAO<Usuario> {

    private Connection conn = null;

    public LoginDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    /**
     * @param usuario;
     * @throws DBException;
     */
    @Override
    public void cadastrar(Usuario usuario)  throws SQLException {
        String inserirUsuario = "INSERT INTO usuario(nome,cpf,email,senha,tipo_do_usuario) VALUES (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(inserirUsuario,Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, usuario.getNome());
            preparedStatement.setString(2, usuario.getCpf());
            preparedStatement.setString(3, usuario.getEmail().getEndereco());
            preparedStatement.setString(4, usuario.getSenha());
            preparedStatement.setString(5, usuario.getTipoDoUsuario().name());

            preparedStatement.executeUpdate();

            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                if(resultSet.next()){
                    Long idUsuario = resultSet.getLong(1);
                    usuario.setId(idUsuario);
                }
            }

        }
    }

    /**
     * @param email;
     * @param senha;
     * @throws UsuarioNaoEncontradoException;
     */
    @Override
    public Usuario autenticar(String email, String senha)  throws SQLException {
        String loginUsuario = "SELECT usr.id, usr.nome, usr.cpf, usr.email, usr.senha, usr.tipo_do_usuario FROM usuario usr WHERE email = ?";
        try(PreparedStatement preparedStatement = conn.prepareStatement(loginUsuario)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()) {
                    TipoDoUsuario tipoDoUsuario = TipoDoUsuario.valueOf(resultSet.getString("tipo_do_usuario").toUpperCase());
                    if (!senha.equals(resultSet.getString("senha"))) {
                        throw new SenhaInvalidaException("Senha inválida!");
                    }
                    switch (tipoDoUsuario) {
                        case ADMIN:
                            return new Admin(resultSet.getString("nome"), resultSet.getString("cpf"), new Email(resultSet.getString("email")), resultSet.getString("senha"));
                        case CLIENTE:
                            return new Cliente(resultSet.getString("nome"), resultSet.getString("cpf"), new Email(resultSet.getString("email")), resultSet.getString("senha"));
                        case GERENTE:
                            return new Gerente(resultSet.getString("nome"), resultSet.getString("cpf"), new Email(resultSet.getString("email")), resultSet.getString("senha"));
                    }
                }
            }
            throw new UsuarioNaoEncontradoException("usuario não encontrado");
        }
    }

    /**
     * @param usuario;
     * @throws EmailExistenteException;
     * @throws UsuarioJaCadastradoException;
     */
    @Override
    public boolean validarSeExistePorEmailOuCpf(Usuario usuario) throws SQLException {

        // Tratar regra de negócio, essa classe não deve tratar regras de negócio.
        String validarDados =
                "SELECT usr.email, usr.cpf FROM usuario usr " +
                        "WHERE ? = usr.email OR ? = usr.cpf;";

        try(PreparedStatement preparedStatement = conn.prepareStatement(validarDados)) {
            preparedStatement.setString(1, usuario.getEmail().getEndereco());
            preparedStatement.setString(2, usuario.getCpf());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }

        }
    }




}
