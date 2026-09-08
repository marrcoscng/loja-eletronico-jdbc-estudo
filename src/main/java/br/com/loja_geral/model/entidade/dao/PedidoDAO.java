package br.com.loja_geral.model.entidade.dao;

import br.com.loja_geral.model.entidade.Pedido;

public interface PedidoDAO {

    void atualizarPedido(String codigo);
    void cancelarPedido(String codigo);

}
