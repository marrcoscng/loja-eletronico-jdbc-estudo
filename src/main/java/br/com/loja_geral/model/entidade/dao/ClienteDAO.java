package br.com.loja_geral.model.entidade.dao;

import br.com.loja_geral.model.entidade.Usuario;

import java.util.ArrayList;
import java.util.Optional;

public interface ClienteDAO< T extends Usuario> {

    void salvar(T usuario);
    void atualizar(T usuarioAtualizado);
    Optional<T> buscarPorId(long id);
    ArrayList<T> buscarTodos();
    void deletarId(long id);

}
