package br.com.loja_geral.dao;

import br.com.loja_geral.model.Usuario;

public interface LoginDAO<T extends Usuario> {

    void cadastrar(T usuario);
    T autenticar (String email, String senha);
}
