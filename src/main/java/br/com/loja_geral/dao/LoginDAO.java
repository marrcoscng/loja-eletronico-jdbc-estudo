package br.com.loja_geral.dao;

import br.com.loja_geral.model.Usuario;

import java.sql.SQLException;

public interface LoginDAO<T extends Usuario> {

    void cadastrar(T usuario)  throws SQLException;
    T autenticar (String email, String senha) throws SQLException;
    boolean validarSeExistePorEmailOuCpf(T usuario) throws SQLException;
}
