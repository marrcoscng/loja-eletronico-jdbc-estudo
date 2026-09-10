package br.com.loja_geral.dao;

public interface PedidoDAO {

    void atualizarPedido(String codigo);
    void cancelarPedido(String codigo);

}
