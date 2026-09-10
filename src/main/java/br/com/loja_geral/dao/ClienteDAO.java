package br.com.loja_geral.dao;

import br.com.loja_geral.model.Usuario;

import java.util.ArrayList;
import java.util.Optional;

public interface ClienteDAO< T extends Usuario> {

    void salvar(T usuario);
    void atualizar(T usuarioAtualizado);
    Optional<T> buscarPorId(Long id);
    ArrayList<T> buscarTodos();
    void deletarId(Long id);

}
