package br.com.loja_geral.dao;

import br.com.loja_geral.model.Pedido;
import br.com.loja_geral.model.enums.StatusPedido;

import java.util.List;

public interface PedidoDAO <T extends Pedido>{

    Pedido buscarPedidoPorId(Long idPedido);
    List<Pedido> listaDePedidsoPorClienteId(Long idCliente);
    List<Pedido> listaDePedidosPorStatus(StatusPedido statusPedido);
    String obterCodigoDeRastreio(Long idPedido);
    void atualizarStatusPedido(Long idPedido, StatusPedido novoStatus);
    void marcarComoEntregue(Long idPedido);
    void cancelarPedido(Long idPedido);
    void salvarPedido(T pedido);


}
