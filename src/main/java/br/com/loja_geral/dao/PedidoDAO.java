package br.com.loja_geral.dao;

import br.com.loja_geral.model.Pedido;

public interface PedidoDAO <T extends Pedido>{

    void atualizarPedido(String codigo);
    void cancelarPedido(String codigo);
    void finalizarPedido(T pedido);

}
