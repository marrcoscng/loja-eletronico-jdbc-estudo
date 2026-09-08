package br.com.loja_geral.model.entidade.dao;

import br.com.loja_geral.model.entidade.Email;
import br.com.loja_geral.model.entidade.Usuario;

public interface LoginDAO<T extends Usuario> {

    void cadastrar(T usuario);
    T autenticar (String email, String senha);
}
